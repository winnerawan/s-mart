<?php
/**
 */
namespace App\Support\Response\Json\Model;

use JsonSerializable;

/**
 */
class Error implements JsonSerializable
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
	public function __construct($message = '', $code = 0){
		$this->code = $code >> 0;
		$this->message = $message;
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