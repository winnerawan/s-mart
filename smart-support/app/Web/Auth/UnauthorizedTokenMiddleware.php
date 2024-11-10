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
class UnauthorizedTokenMiddleware extends TokenMiddleware
{
	/**
	 */
	protected function handleAuthorized(Request $request, Closure $next){
		if ($request->expectsJson()) {
			return JsonHijackingResponse::error(JsonHijackingResponse::HTTP_SERVICE_UNAVAILABLE)->build();
		}
		$authorized = Config::get('auth.redirect.authorized');
		if ($authorized != null) {
			return Redirect::route($authorized);
		}
		abort(JsonHijackingResponse::HTTP_SERVICE_UNAVAILABLE);
	}
}