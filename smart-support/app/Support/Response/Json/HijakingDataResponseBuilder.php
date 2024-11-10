<?php
/**
 */
namespace App\Support\Response\Json;

use App\Support\Response\JsonHijackingResponse;

/**
 */
class HijakingDataResponseBuilder extends DataResponseBuilder
{
	/**
	 */
	public function build(){
		return new JsonHijackingResponse($this->getData(), $this->getStatus()->getCode());
	}
}