<?php
/**
 */
namespace App\Service\Api\User;

use Closure;
use Illuminate\Http\Request;
use App\Support\Response\JsonResponse;

/**
 */
class IsUserTypeOf
{
	/**
	 */
	public function handle(Request $request, Closure $next, $userTypeId = 0){
		if (User::getUser()->user_type_id != $userTypeId) {
			return JsonResponse::error(JsonResponse::HTTP_SERVICE_UNAVAILABLE)
				->build();
		}
		return $next($request);
	}
}