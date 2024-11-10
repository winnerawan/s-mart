<?php
/**
 */
namespace App\Web\Csrf;

use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Redirect;
use App\Support\Response\JsonHijackingResponse;
use App\Web\Config;

/**
 */
class VerifyTokenMiddleware
{
	/**
	 */
	const PARAM_NAME = '_token';
	const HEADER_NAME = 'X-Csrf-Token';

	/**
	 */
	public function handle(Request $request, Closure $next){
		return $this->match($request) ? $next($request) : $this->response($request);
	}
	
	/**
	 */
	protected function response(Request $request){
		if ($request->expectsJson()) {
			return JsonHijackingResponse::error(JsonHijackingResponse::HTTP_BAD_REQUEST)->build();
		}
		$redirect = Config::get('csrf.redirect');
		if ($redirect != null) {
			return Redirect::route($redirect);
		}
		abort(JsonHijackingResponse::HTTP_BAD_REQUEST);
	}
	
	/**
	 */
	protected function match(Request $request){
		$token = $this->token($request);
		return is_string($token) && is_string($value = $request->session()->token()) && hash_equals($value, $token);
	}
	
	/**
	 */
	protected function token(Request $request){
		return $request->input(static::PARAM_NAME) ?: $request->header(static::HEADER_NAME);
	}
}