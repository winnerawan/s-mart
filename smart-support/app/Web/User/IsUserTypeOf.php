<?php
/**
 */
namespace App\Web\User;

use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Redirect;

/**
 */
class IsUserTypeOf
{
	/**
	 */
	public function handle(Request $request, Closure $next, $userTypeId = 0){
		if (User::getUser()->user_type_id != $userTypeId) {
			return Redirect::route('/');
		}
		return $next($request);
	}
}