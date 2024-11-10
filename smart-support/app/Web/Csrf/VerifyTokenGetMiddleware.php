<?php
/**
 */
namespace App\Web\Csrf;

use Illuminate\Http\Request;

/**
 */
class VerifyTokenGetMiddleware extends VerifyTokenMiddleware
{
	/**
	 */
	protected function token(Request $request){
		return $request->query->get(static::PARAM_NAME) ?: $request->header(static::HEADER_NAME);
	}
}