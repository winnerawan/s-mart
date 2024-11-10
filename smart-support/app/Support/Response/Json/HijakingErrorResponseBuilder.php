<?php
/**
 */
namespace App\Support\Response\Json;

use App\Support\Response\JsonHijackingResponse;

/**
 */
class HijakingErrorResponseBuilder extends ErrorResponseBuilder
{
	/**
	 */
	public function build(){
		return new JsonHijackingResponse($this->getData(), $this->getStatus()->getCode());
	}
}