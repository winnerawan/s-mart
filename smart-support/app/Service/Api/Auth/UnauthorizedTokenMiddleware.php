<?php
/**
 */
namespace App\Service\Api\Auth;

use Closure;
use Illuminate\Http\Request;
use App\Support\Response\JsonResponse;

/**
 */
class UnauthorizedTokenMiddleware extends TokenMiddleware
{
	/**
	 */
	protected function handleAuthorized(Request $request, Closure $next){
		if ($request->expectsJson()) {
			return JsonResponse::error(JsonResponse::HTTP_SERVICE_UNAVAILABLE)->build();
		}
		abort(JsonResponse::HTTP_SERVICE_UNAVAILABLE);
	}
}