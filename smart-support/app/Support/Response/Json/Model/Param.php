<?php
/**
 */
namespace App\Support\Response\Json\Model;

use JsonSerializable;

/**
 */
class Param implements JsonSerializable
{
	/**
	 */
	const KEY_NAME = 'name';
	const KEY_MESSAGE = 'message';

	/**
	 */
	protected $name;
	protected $message;

	/**
	 */
	public function __construct($message = '', $name = ''){
		$this->name = $name;
		$this->message = $message;
	}

	/**
	 */
	public function getName(){
		return $this->name;
	}

	/**
	 */
	public function getMessage(){
		return $this->message;
	}

	/**
	 */
	public function jsonSerialize(){
		$data[static::KEY_NAME] = $this->name;
		$data[static::KEY_MESSAGE] = $this->message;
		return $data;
	}
}