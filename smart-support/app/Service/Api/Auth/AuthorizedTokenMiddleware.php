<?php
/**
 */
namespace App\Service\Api\Auth;

use Closure;
use Illuminate\Http\Request;
use App\Support\Response\JsonResponse;

/**
 */
class AuthorizedTokenMiddleware extends TokenMiddleware
{
	/**
	 */
	protected function handleUnauthorized(Request $request, Closure $next){
		if ($request->expectsJson()) {
			return JsonResponse::error(JsonResponse::HTTP_UNAUTHORIZED)->build();
		}
		abort(JsonResponse::HTTP_UNAUTHORIZED);
	}
}