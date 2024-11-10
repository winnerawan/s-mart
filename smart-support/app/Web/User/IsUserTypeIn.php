<?php
/**
 */
namespace App\Web\User;

use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Redirect;

/**
 */
class IsUserTypeIn
{
	/**
	 */
	public function handle(Request $request, Closure $next, ...$userTypeIds){
		if (!in_array(User::getUser()->user_type_id, $userTypeIds, false)) {
			return Redirect::route('/');
		}
		return $next($request);
	}
}