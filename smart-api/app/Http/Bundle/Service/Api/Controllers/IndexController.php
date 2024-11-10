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
	public function gender(Request $request){
		$gender = new Models\Gender($request);
		if (!$gender->validate()) {
			return $this->jsonError()
				->params($gender->getValidator()->errors())
				->build();
		}
		return $this->jsonData($gender->response())
			->build();
	}

	/**
	 */
	public function familyRelationship(Request $request){
		$familyRelationship = new Models\FamilyRelationship($request);
		if (!$familyRelationship->validate()) {
			return $this->jsonError()
				->params($familyRelationship->getValidator()->errors())
				->build();
		}
		return $this->jsonData($familyRelationship->response())
			->build();
	}


	/**
	 */
	public function religion(Request $request){
		$religion = new Models\Religion($request);
		if (!$religion->validate()) {
			return $this->jsonError()
				->params($religion->getValidator()->errors())
				->build();
		}
		return $this->jsonData($religion->response())
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
	public function inventoryCondition(Request $request){
		$inventoryCondition = new Models\InventoryCondition($request);
		if (!$inventoryCondition->validate()) {
			return $this->jsonError()
				->params($inventoryCondition->getValidator()->errors())
				->build();
		}
		return $this->jsonData($inventoryCondition->response())
			->build();
	}

	/**
	 */
	public function securityShift(Request $request){
		$securityShift = new Models\SecurityShift($request);
		if (!$securityShift->validate()) {
			return $this->jsonError()
				->params($securityShift->getValidator()->errors())
				->build();
		}
		return $this->jsonData($securityShift->response())
			->build();
	}


	/**
	 */
	public function block(Request $request){
		$block = new Models\Block($request);
		if (!$block->validate()) {
			return $this->jsonError()
				->params($block->getValidator()->errors())
				->build();
		}
		return $this->jsonData($block->response())
			->build();
	}


	/**
	 */
	public function home(Request $request){
		$home = new Models\Home($request);
		if (!$home->validate()) {
			return $this->jsonError()
				->params($home->getValidator()->errors())
				->build();
		}
		return $this->jsonData($home->response())
			->build();
	}

	/**
	 */
	public function unit(Request $request){
		$unit = new Models\Unit($request);
		if (!$unit->validate()) {
			return $this->jsonError()
				->params($unit->getValidator()->errors())
				->build();
		}
		return $this->jsonData($unit->response())
			->build();
	}

	/**
	 */
	public function relationshipStatus(Request $request){
		$relationshipStatus = new Models\RelationshipStatus($request);
		if (!$relationshipStatus->validate()) {
			return $this->jsonError()
				->params($relationshipStatus->getValidator()->errors())
				->build();
		}
		return $this->jsonData($relationshipStatus->response())
			->build();
	}

	/**
	 */
	public function hasAccount(Request $request){
		$hasAccount = new Models\HasAccount($request);
		if (!$hasAccount->validate()) {
			return $this->jsonError()
				->params($hasAccount->getValidator()->errors())
				->build();
		}
		return $this->jsonData($hasAccount->response())
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
