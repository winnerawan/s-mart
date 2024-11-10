<?php
/**
 */
namespace App\Service\Api\User;

use Closure;
use Illuminate\Http\Request;
use App\Support\Response\JsonResponse;

/**
 */
class IsUserTypeIn
{
	/**
	 */
	public function handle(Request $request, Closure $next, ...$userTypeIds){
		if (!in_array(User::getUser()->user_type_id, $userTypeIds, false)) {
			return JsonResponse::error(JsonResponse::HTTP_SERVICE_UNAVAILABLE)
				->build();
		}
		return $next($request);
	}
}