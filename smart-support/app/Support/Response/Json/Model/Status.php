<?php
/**
 */
namespace App\Support\Response\Json\Model;

use JsonSerializable;
use App\Support\Response\JsonResponse;

/**
 */
class Status implements JsonSerializable
{
	/**
	 */
	const KEY_CODE = 'code';
	const KEY_MESSAGE = 'message';
	
	/**
	 */
	protected $code;
	protected $message;

	/**
	 */
	public function __construct($code = JsonResponse::HTTP_OK){
		$this->code = $code;
		$this->message = JsonResponse::$statusTexts[$code];
	}

	/**
	 */
	public function getCode(){
		return $this->code;
	}

	/**
	 */
	public function getMessage(){
		return $this->message;
	}

	/**
	 */
	public function jsonSerialize(){
		$data[static::KEY_CODE] = $this->code;
		$data[static::KEY_MESSAGE] = $this->message;
		return $data;
	}
}