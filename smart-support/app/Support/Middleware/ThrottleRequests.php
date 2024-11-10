<?php
/**
 */
namespace App\Support\Middleware;

use Closure;
use Carbon\Carbon;
use Illuminate\Routing\Middleware;
use Symfony\Component\HttpFoundation\Response;

/**
 */
class ThrottleRequests extends Middleware\ThrottleRequests
{
	/**
	 */
	protected $request;

	/**
	 */
	public function handle($request, Closure $next, $maxAttempts = 60, $decayMinutes = 1, $prefix = ''){
		$this->request = $request;
		return parent::handle($request, $next, $maxAttempts, $decayMinutes);
	}
	
	/**
	 */
	protected function buildResponse($key, $maxAttempts){
		$retryAfter = $this->limiter->availableIn($key);
		return $this->addHeaders($this->createResponse(), $maxAttempts, null, $retryAfter);
	}
	
	/**
	 */
	protected function createResponse(){
		return new Response('Too Many Attempts.', Response::HTTP_TOO_MANY_REQUESTS);
	}

	/**
	 */
	protected function addHeaders(Response $response, $maxAttempts, $remainingAttempts, $retryAfter = null){
		if (!is_null($retryAfter)) {
			$headers['Retry-After'] = $retryAfter;
			$headers['X-RateLimit-Reset'] = Carbon::now()->getTimestamp() + $retryAfter;
			$response->headers->add($headers);
		}
		return $response;
	}

	/**
	 */
	protected function calculateRemainingAttempts($key, $maxAttempts, $retryAfter = null){
		/* ignore */
		return null;
	}
}