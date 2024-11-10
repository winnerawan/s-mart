<?php
/**
 */
namespace App\Support\Middleware;

use App\Support\Response\JsonResponse;

/**
 */
class ApiThrottleRequests extends ThrottleRequests
{
	/**
	 */
	protected function createResponse(){
		$statusCode = JsonResponse::HTTP_TOO_MANY_REQUESTS;
		if ($this->request->expectsJson()) {
			return JsonResponse::error($statusCode)->build();
		}
		abort($statusCode);
	}
}