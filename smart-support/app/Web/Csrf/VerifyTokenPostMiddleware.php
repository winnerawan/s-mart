<?php
/**
 */
namespace App\Web\Csrf;

use Illuminate\Http\Request;

/**
 */
class VerifyTokenPostMiddleware extends VerifyTokenMiddleware
{
	/**
	 */
	protected function token(Request $request){
		return $request->request->get(static::PARAM_NAME) ?: $request->header(static::HEADER_NAME);
	}
}