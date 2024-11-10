<?php
/**
 */
namespace App\Web\View;

use App\Support\View\PageView;
use App\Web\User\UserModel;

/**
 */
class UserPageView extends PageView
{
	/**
	 */
	public function render(){
		return parent::render()->with('__user', new UserModel());
	}
}