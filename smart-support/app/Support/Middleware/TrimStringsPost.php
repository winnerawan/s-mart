<?php
/**
 */
namespace App\Support\Middleware;

/**
 */
class TrimStringsPost extends TrimStrings
{
	/**
	 */
	protected $except = [
		'password',
		'password_confirmation'
	];

	/**
	 */
	protected function clean($request){
		$this->cleanParameterBag($request->request);
	}
}