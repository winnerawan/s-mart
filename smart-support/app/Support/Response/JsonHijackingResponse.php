<?php
/**
 */
namespace App\Support\Response;

use Illuminate\Http;

/**
 */
class JsonHijackingResponse extends Http\JsonResponse
{
	/**
	 */
	const PREFIX = "";
	const SUFFIX = '';

	/**
	 */
	public function setContent($content){
		$content = static::PREFIX . (string)$content . static::SUFFIX;
		parent::setContent($content);
	}
	
	/**
	 */
	public static function success($status = null){
		return new Json\HijakingSuccessResponseBuilder($status);
	}

	/**
	 */
	public static function error($status = null){
		return new Json\HijakingErrorResponseBuilder($status);
	}

	/**
	 */
	public static function data($data){
		return new Json\HijakingDataResponseBuilder($data);
	}
}