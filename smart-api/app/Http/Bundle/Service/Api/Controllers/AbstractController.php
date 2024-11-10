<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Routing\Controller;
use Illuminate\Foundation\Auth\Access\AuthorizesRequests;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Foundation\Validation\ValidatesRequests;
use App\Support\Response\JsonResponse;

/**
 */
abstract class AbstractController extends Controller
{
	/**
	 */
	use AuthorizesRequests, DispatchesJobs, ValidatesRequests;

	/**
	 */
	protected function jsonSuccess($status = null){
		return JsonResponse::success($status);
	}

	// /**
	//  */
	// protected function jsonSuccessMediaUid($status = null){
	// 	return JsonResponse::success($status);
	// }

	/**
	 */
	protected function jsonError($status = null){
		return JsonResponse::error($status);
	}

	/**
	 */
	protected function jsonData($data){
		return JsonResponse::data($data);
	}
}
