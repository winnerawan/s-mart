<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;
use App\Service;

/**
 */
class SignIn extends Support\Validator\AbstractValidator
{
	/**
	 */
	protected $validatorRules = [
		'username' => 'required|string',
		'password' => 'required|string'
	];

	/**
	 */
	protected $user;
	protected $token;
	protected $errorField = 'error';
	protected $errorMessage = 'The username and password you entered did not match our records.';

	/**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
		// $branch = Smart\Branch::select()
		// 	->where('branch.id', 1)
		// 	->where('branch.branch_status_id',Smart\BranchStatus::STATUS_ACTIVE_ID)
		// 	->first();
		// if (!$branch) {
		// 	$this->validator->errors()->add($this->errorField, $this->errorMessage);
		// 	return false;
		// }
		// $user = $branch->userBranchs()->select('user_branch.*')
		// 	->join('user','user.id','=','user_branch.user_id')
		$user = Smart\User::select('user.*')
			->join('username','username.user_id','=','user.id')
			->whereIn('user.user_status_id', [Smart\UserStatus::STATUS_ACTIVE_ID, Smart\UserStatus::STATUS_INACTIVE_ID])
			->where('username.username', $this->validatorData->get('username'))
			->first();
		// if (!$userBranch) {
		// 	$this->validator->errors()->add($this->errorField, $this->errorMessage);
		// 	return false;
		// }
		// $user = //$userBranch->getUser();
		$status = $user->getPassword()->isPassword($this->validatorData->get('password'));
		if (!$status) {
			$this->validator->errors()->add($this->errorField, $this->errorMessage);
			return false;
		}
		$this->user = $user;
		return true;
	}

	/**
	 */
	public function response(){
		return Smart\User::transaction(function(){
			$this->removePreviousToken();
			$this->createToken();
			return $this->createResponse();
		});
	}

	/**
	 */
	protected function removePreviousToken() {
		$username = $this->user->getUsername();
		$username->device = $this->validatorData->get('device');
		$username->save();

		if(!$this->validatorData->get('username')=='guest@example.com') {
			$tokens = Smart\Token::where('user_id', $this->user->id)
				->where('token_status_id', Smart\TokenStatus::STATUS_REVOKED_ID)
			->get();
			foreach($tokens as $token) {
				$token->delete();
			}
		}
	}

	/**
	 */
	protected function createToken(){
		$created = Carbon::now();
		$expired = Carbon::parse($created)->addYear(3);
		/**/
		$this->token = new Smart\Token();
		$this->token->user_id = $this->user->id;
		$this->token->token_type_id = Smart\TokenType::TYPE_BEARER_ID;
		$this->token->token_status_id = Smart\TokenStatus::STATUS_ACTIVE_ID;
		$this->token->prefix = $created->format(Support\Uid::FORMAT);
		$this->token->suffix = $expired->format(Support\Uid::FORMAT);
		$this->token->unique = Support\Uid::randomAlpha(32);
		$this->token->created = $created;
		$this->token->expired = $expired;
		$this->token->created_user_id = $this->user->id;
		$this->token->save();
	}

	/**
	 */
	protected function createResponse(){
		$response['user']['id'] = $this->user->id;
		$response['user']['type'] = $this->user->getUserType()->id;
		$response['user']['username'] = $this->user->getUsername()->username;
		$response['user']['name'] = $this->user->name;
		// $response['user']['is_verified'] = $this->user->getUserStatus()->isStatusActive();
		// $response['user']['role'] = $this->user->getUserRole() != null ? $this->user->getUserRole()->getRole()->name : null;
		// $response['user']['image'] = $this->user->getUserPhoto()  != null ? sprintf('https:%s%s', Api\Config::get('media.file'), $this->user->getUserPhoto()->getMedia()->file) : null;
		// $response['user']['home'] = $this->user->getUserHome() != null ? sprintf('%s %s', $this->user->getUserHome()->getHome()->getBlock()->name, $this->user->getUserHome()->getHome()->name) : null;

		/**/
		$response['token']['access_type'] = 'Bearer';
		$response['token']['access_token'] = (string)Service\Api\Auth\TokenModel::fromToken($this->token)->toJwt();
		$response['token']['refresh_type'] = null;
		$response['token']['refresh_token'] = null;
		return $response;
	}
}
