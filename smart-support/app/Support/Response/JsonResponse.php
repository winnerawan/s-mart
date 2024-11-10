<?php
/**
 */
namespace App\Support\Response;

use Illuminate\Http;

/**
 */
class JsonResponse extends Http\JsonResponse
{
	/**
	 */
	public static function success($status = null){
		return new Json\SuccessResponseBuilder($status);
	}
	
	/**
	 */
	public static function error($status = null){
		return new Json\ErrorResponseBuilder($status);
	}

	/**
	 */
	public static function data($data){
		return new Json\DataResponseBuilder($data);
	}
}