<?php
/**
 */
namespace App\Http;

use App;
use Illuminate\Foundation\Http\Kernel as HttpKernel;
/**
 */
class Kernel extends HttpKernel
{
    /**
     * The application's route middleware groups.
     *
     * @var array
     */
    protected $middlewareGroups = [
        'api' => [
            // 'throttle:60,1',
        ],
    ];

    /**
     * The application's route middleware.
     *
     * These middleware may be assigned to groups or used individually.
     *
     * @var array
     */
    protected $routeMiddleware = [
        /**/
        'support.throttleRequests' => App\Support\Middleware\ThrottleRequests::class,
        'support.apiThrottleRequests' => App\Support\Middleware\ApiThrottleRequests::class,
        'support.webThrottleRequests' => App\Support\Middleware\WebThrottleRequests::class,
        /**/
        'support.trimStrings' => App\Support\Middleware\TrimStrings::class,
        'support.trimStringsGet' => App\Support\Middleware\TrimStringsGet::class,
        'support.trimStringsPost' => App\Support\Middleware\TrimStringsPost::class,
        /**/
        'api.auth.token' => App\Service\Api\Auth\TokenMiddleware::class,
        'api.auth.authorizedToken' => App\Service\Api\Auth\AuthorizedTokenMiddleware::class,
        'api.auth.unauthorizedToken' => App\Service\Api\Auth\UnauthorizedTokenMiddleware::class,
        /**/
        'api.user.isUserTypeOf' => App\Service\Api\User\IsUserTypeOf::class,
        'api.user.isUserTypeIn' => App\Service\Api\User\IsUserTypeIn::class
    ];
}
