<?php
/**
 */
namespace App\Support\Middleware;

/**
 */
class TrimStringsGet extends TrimStrings
{
	/**
	 */
	protected function clean($request){
		$this->cleanParameterBag($request->query);
	}
}