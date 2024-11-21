<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Http\Request;
use App\Http\Bundle\Service\Api\Models;

/**
 */
class IndexController extends AbstractController
{
    /**
	 */
	public function index(){
		$response['version'] = 1;
		$response['message'] = 'Congratulations! You have found an empty page! Go celebrate this great discovery by sending the admin a coffee...';// url('');

		return $this->jsonData($response)
			->build();
    }

	/**
	 */
	public function userTypes(Request $request){
		$userTypes = new Models\UserType($request);
		if (!$userTypes->validate()) {
			return $this->jsonError()
				->params($userTypes->getValidator()->errors())
				->build();
		}
		return $this->jsonData($userTypes->response())
			->build();
	}

	/**
	 */
	public function signin(Request $request){
		$signin = new Models\SignIn($request);
		if (!$signin->validate()) {
			return $this->jsonError()
				->params($signin->getValidator()->errors())
				->build();
		}
		return $this->jsonData($signin->response())
			->build();
	}

	/**
	 */
	public function signup(Request $request){
		$signup = new Models\SignUp($request);
		if (!$signup->validate()) {
			return $this->jsonError()
				->params($signup->getValidator()->errors())
				->build();
		}
		$signup->create();
		return $this->jsonSuccess()
			->build();
	}

	/**
	 */
	public function signout(Request $request){
		$signout = new Models\SignOut($request);
		if (!$signout->validate()) {
			return $this->jsonError()
				->params($signout->getValidator()->errors())
				->build();
		}
		$signout->signout();
		return $this->jsonSuccess()
			->build();
	}
}
