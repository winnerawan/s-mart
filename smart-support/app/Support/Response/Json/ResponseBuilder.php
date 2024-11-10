<?php
/**
 */
namespace App\Support\Response\Json;

use App\Support\Response\JsonResponse;

/**
 */
abstract class ResponseBuilder
{
	/**
	 */
	const KEY_ERROR = 'error';
	const KEY_PARAM = 'param';
	const KEY_PARAMS = 'params';
	const KEY_STATUS = 'status';
	
	/**
	 */
	public function build(){
		return new JsonResponse($this->getData(), $this->getStatus()->getCode());
	}
	
	/**
	 */
	abstract public function getData();

	/**
	 */
	abstract public function getStatus();
}