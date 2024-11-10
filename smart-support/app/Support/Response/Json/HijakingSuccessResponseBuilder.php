<?php
/**
 */
namespace App\Support\Response\Json;

use App\Support\Response\JsonHijackingResponse;

/**
 */
class HijakingSuccessResponseBuilder extends SuccessResponseBuilder
{
	/**
	 */
	public function build(){
		return new JsonHijackingResponse($this->getData(), $this->getStatus()->getCode());
	}
}