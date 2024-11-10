<?php
/**
 */
namespace App\Http\Bundle\Service\Providers;

use Illuminate\Support\ServiceProvider;
/**
 */
class AppServiceProvider extends ServiceProvider
{
    /**
     * jwt issue 
     * https://github.com/lcobucci/jwt/issues/550#issuecomment-733557709
     */
    // public function boot() {
    //     if (config('app.debug')) {
    //         error_reporting(E_ALL & ~E_USER_DEPRECATED);
    //     } else {
    //         error_reporting(0);
    //     }
    // }
}
