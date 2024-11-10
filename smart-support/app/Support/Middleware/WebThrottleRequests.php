<?php
/**
 */
namespace App\Support\Middleware;

use App\Support\Response\JsonHijackingResponse;

/**
 */
class WebThrottleRequests extends ThrottleRequests
{
	/**
	 */
	protected function createResponse(){
		$statusCode = JsonHijackingResponse::HTTP_TOO_MANY_REQUESTS;
		if ($this->request->expectsJson()) {
			return JsonHijackingResponse::error($statusCode)->build();
		}
		abort($statusCode);
	}
}