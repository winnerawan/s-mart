<?php
/**
 */
namespace App\Service\Api\Auth;

use Closure;
use Illuminate\Http\Request;
use App\Service\Api\User\User;

/**
 */
class TokenMiddleware
{
	/**
	 */
	public function handle(Request $request, Closure $next){
		return $this->validateToken($request) && $this->validateUser($request) ?
			$this->handleAuthorized($request, $next) :
			$this->handleUnauthorized($request, $next);
	}
	
	/**
	 */
	protected function validateUser(Request $request){
		return User::setup(Token::getToken());
	}

	/**
	 */
	protected function validateToken(Request $request){
		return Token::setup($request->bearerToken());
	}
	
	/**
	 */
	protected function handleAuthorized(Request $request, Closure $next){
		return $next($request);
	}

	/**
	 */
	protected function handleUnauthorized(Request $request, Closure $next){
		return $next($request);
	}
}