<?php
/**
 */
namespace App\Web\Auth;

use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Redirect;
use App\Support\Response\JsonHijackingResponse;
use App\Web\Config;

/**
 */
class AuthorizedTokenMiddleware extends TokenMiddleware
{
	/**
	 */
	protected function handleUnauthorized(Request $request, Closure $next){
		if ($request->expectsJson()) {
			return JsonHijackingResponse::error(JsonHijackingResponse::HTTP_UNAUTHORIZED)->build();
		}
		$unauthorized = Config::get('auth.redirect.unauthorized');
		if ($unauthorized != null) {
			return Redirect::route($unauthorized);
		}
		abort(JsonHijackingResponse::HTTP_UNAUTHORIZED);
	}
}