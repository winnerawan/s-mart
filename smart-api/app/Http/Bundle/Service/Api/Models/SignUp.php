<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models;

use Carbon\Carbon;
use Illuminate\Http\Request;
use Wayang\Stdlib\Nik\Nik;
use App\Database\Smart;
use App\Database\Smart\UserSecurityDetail;
use App\Database\Smart\UserType;
use App\Service\Api;
use App\Support;
use App\Service;
use Exception;

/**
 */
class SignUp extends Support\Validator\AbstractValidator
{
	/**
	 */
	protected $validatorRules = [
        'fullname' => 'required|string',
        'phone' => 'nullable|string|unique:user',
		'nik' => 'nullable|string|unique:user',
        'password' => 'required|string',
		'username' => 'required|string|unique:username',
		'address' => 'nullable|string',
		'number' => 'nullable|string',
		'gender' => 'nullable|numeric',
		'religion' => 'nullable|numeric',
		'block' => 'nullable|numeric',
		'home' => 'nullable|numeric',
		'identity' => 'nullable|numeric'
    ];
    
    /**
	 */
	protected $userType;// = Smart\UserType::TYPE_MEMBER_ID;


	/**
	 */
    protected $isExists;
    protected $name;
    protected $newUser;
	protected $newUserBranch;
	protected $newUserHome;
	protected $newUserUsername;
	protected $newUserPassword;
    protected $newAccount;
    protected $firstName;
    protected $lastName;
    protected $username;
	protected $token;
	protected $branch;
	protected $block;
	protected $home;
	protected $phone;
	protected $relationship;
	protected $errorField = 'error';
	protected $errorMessage = 'Ooops...something went wrong!';

	/**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
        }
        $this->username = $this->validatorData->get('username');
        if (!$this->validateUsername() || !$this->validateName()
			|| !$this->validateUserType() ||!$this->validatePhone() ||!$this->validateRelationship()
			|| !$this->validateBlock() || !$this->validateHome()
			) 
			{
			return false;
		}
		$this->branch = 1;
        return true;
    }

	/**
	 */
	protected function validateUserType(){
		$this->userType = Smart\UserType::where('id', $this->validatorData->get('type'))
			->first();
		if (!$this->userType) {
			$this->validator->errors()->add('userType', 'The userType not found.');
			return false;
        }
        return true;
	}
	/**
	 */
	protected function validateBranch(){
		$this->branch = Smart\Branch::where('id', $this->validatorData->get('branch'))
			->where('branch.branch_status_id', Smart\BranchStatus::STATUS_ACTIVE_ID)
			->first();
		if (!$this->branch) {
			$this->validator->errors()->add('branch', 'The branch not found.');
			return false;
        }
        return true;
	}
	/**
	 */
	protected function validateRelationship(){
		$this->relationship = Smart\FamilyRelationship::where('id', $this->validatorData->get('relationship'))
			->first();
		if (!$this->relationship) {
			// $this->validator->errors()->add('relationship', 'The relationship not found.');
			return true;
        }
        return true;
	}

	/**
	 */
	protected function validateBlock(){
		$this->block = Smart\Block::where('id', $this->validatorData->get('block'))
			->first();
		if (!$this->block) {
			// $this->validator->errors()->add('block', 'The block not found.');
			return true;
        }
        return true;
	}

	/**
	 */
	protected function validateHome(){
		$this->home = Smart\Home::where('block_id', $this->block->id)
			->where('id', $this->validatorData->get('home'))
			->first();
		if (!$this->home) {
			// $this->validator->errors()->add('home', 'The home not found.');
			return true;
        }
        return true;
	}

    /**
	 */
	protected function validateUsername()
	{
		$this->username = $this->validatorData->get('username');
		$this->isExists = Smart\User::select()
			->join('username', 'username.user_id', '=', 'user.id')
			->where('username.username', $this->username)
			->count('user.id');
		if ($this->isExists >= 1) {
			$this->validator->errors()->add('username', 'The username is already been taken.');
			return false;
		}
		if (!!$this->isExists) {
			$this->validator->errors()->add('username', 'The username is already been taken.');
			return false;
		}
		return true;
	}

	// /**
	//  */
	// protected function validateNik(){
	// 	try {
	// 		$this->nik = new Nik($this->validatorData->get('nik'));
	// 	} catch(Exception $e) {
	// 		$this->validator->errors()->add('nik', 'The NIK is not valid.');
	// 		return false;
	// 	}
	// 	return true;
    // }
    /**
	 */
	protected function validateName()
	{
        $name = trim($this->validatorData->get('fullname'));
        $last_name = (strpos($name, ' ') === false) ? '' : preg_replace('#.*\s([\w-]*)$#', '$1', $name);
        $first_name = trim( preg_replace('#'.preg_quote($last_name,'#').'#', '', $name ) );
        $this->firstName = $first_name;
        $this->lastName = $last_name;
        $this->name = $this->name = implode(' ', [$this->firstName, $this->lastName]);
        return true;

    }
    
    /**
	 */
	protected function validatePhone()
	{
		$this->phone = $this->validatorData->get('phone');
		if ($this->phone == null) {
			$this->phone = null;
			return true;
		}
		return true;
	}

    /**
     */
    public function create() {
        return Smart\User::transaction(function(){
			$this->createUser();
			$this->createUsername();
			$this->createPassword();
			$this->createUserHome();
			$this->createUserSecurityDetail();
			$this->sendNotification($this->newUser->name, $this->getAdministrator());
		});
    }

    /**
	 */
	protected function createUser(){
		$this->newUser = new Smart\User();
		$this->newUser->user_type_id = $this->userType->id;
		$this->newUser->user_status_id = Smart\UserStatus::STATUS_INACTIVE_ID;
		// $this->newUser->nik = $this->nik;
		$this->newUser->name = $this->name;
		$this->newUser->phone = $this->phone;
		
		$this->newUser->religion_id = $this->validatorData->get('religion');
		$this->newUser->gender_id = $this->validatorData->get('gender');

		if ($this->validatorData->get('type')==UserType::TYPE_MEMBER_ID || $this->validatorData->get('type')==UserType::TYPE_MEMBER_NON_PERMANENT_ID) {
			$this->newUser->address = $this->block->name;
			$this->newUser->number = $this->home->name;
		}
		if ($this->validatorData->get('type')==UserType::TYPE_MEMBER_ID || $this->validatorData->get('type')==UserType::TYPE_MEMBER_NON_PERMANENT_ID) {
			$this->newUser->family_relationship_id = $this->relationship->id;
		}

		$this->newUser->is_first_name = strlen($this->firstName) > 0;
		$this->newUser->first_name = $this->firstName;
		$this->newUser->is_last_name = strlen($this->lastName) > 0;
		$this->newUser->last_name = $this->lastName;
		$this->newUser->created_user_id = 1;
		$this->newUser->save();

		/**/
		$this->newUserBranch = new Smart\UserBranch();
		$this->newUserBranch->branch_id = $this->branch;
		$this->newUserBranch->user_id = $this->newUser->id;
		$this->newUserBranch->created_user_id = 1;
		$this->newUserBranch->save();

	}

	/**
	 */
	protected function createUserSecurityDetail() {
		if ($this->validatorData->get('type')==UserType::TYPE_SECURITY_ID) {
			$userSecurityDetail = new Smart\UserSecurityDetail();
			$userSecurityDetail->user_id = $this->newUser->id;
			$userSecurityDetail->address = '';
			$userSecurityDetail->nik = '';
			$userSecurityDetail->occupation = 'Security';
			$userSecurityDetail->years_period = 'Bekerja Sejak ...';
			$userSecurityDetail->description = "Bekerja tanpa diperintah. Disiplin tanpa diawasi. Tanggung jawab tanpa diminta.
			“Kami selalu siap siaga dan sepenuh hati menjaga Keamanan Premiere Residence…”";
			$userSecurityDetail->created_user_id = 1;
			$userSecurityDetail->save();
		}
	}

	/**
	 */
	protected function createUsername(){
		$this->newUserUsername = new Smart\Username();
		$this->newUserUsername->user_id = $this->newUser->id;
		$this->newUserUsername->username = $this->username;
		$this->newUserUsername->identity_id = $this->validatorData->get('identity');
		$this->newUserUsername->created_user_id = 1;
		$this->newUserUsername->save();
	}

	/**
	 */
	protected function createPassword(){
		$this->newUserPassword = new Smart\Password();
		$this->newUserPassword->user_id = $this->newUser->id;
		$this->newUserPassword->setPassword($this->validatorData->get('password'));
		$this->newUserPassword->created_user_id = 1;
		$this->newUserPassword->save();
	}

	/**
	 */
	protected function createUserHome(){
		if ($this->validatorData->get('type')==UserType::TYPE_MEMBER_ID || $this->validatorData->get('type')==UserType::TYPE_MEMBER_NON_PERMANENT_ID) {
			$this->newUserHome = new Smart\UserHome();
			$this->newUserHome->user_id = $this->newUser->id;
			$this->newUserHome->home_id = $this->home->id;
			$this->newUserHome->created_user_id = 1;
			$this->newUserHome->save();
		}
	}

    /**
	 */
	public function response() {
        $response['user'] = $this->newUser;
        return $response;
    }

	/**
	 */
	protected function getAdministrator() {
		return Smart\User::join('user_role', 'user_role.user_id', 'user.id')
			->join('username', 'username.user_id', 'user.id')
			->whereIn('user_role.role_id', [1, 3, 999])//todo
			->where('user_role.period_id', $this->getPeriod()->id)
			->pluck('username.username')
			->toArray();
	}

	/**
	 */
	protected function getPeriod() {
		$year = \Carbon\Carbon::now()->format('Y');
		return Smart\Period::select()
			->where('name', $year)
			->first();
	}

	/**
	 */
	public function sendNotification($newUser, $recepients){
        // var_dump($recepients);
		$headings = array (
		  'en' => 'User Baru'
		);
	    $content = array(
		  'en' => strtoupper(sprintf('%s Telah Mendaftar dan membutuhkan verifikasi Anda', $newUser))
	  	);
	  
	  $fields = array(
		'app_id' => Api\Config::get('onesignal.app_id'),
		'include_external_user_ids' => $recepients,
		'data' => array("emergency" => false, "user_verification" => true),
		'contents' => $content,
		'headings' => $headings
	  );
	  
	  $fields = json_encode($fields);
	  
	  $ch = curl_init();
	  curl_setopt($ch, CURLOPT_URL, "https://onesignal.com/api/v1/notifications");
	  $header = sprintf('Authorization: Basic %s', Api\Config::get('onesignal.api_key'));
	  curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', $header));
	  curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);
	  curl_setopt($ch, CURLOPT_HEADER, FALSE);
	  curl_setopt($ch, CURLOPT_POST, TRUE);
	  curl_setopt($ch, CURLOPT_POSTFIELDS, $fields);
	  curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
  
	  $response = curl_exec($ch);
	  curl_close($ch);
  
	  return $response;
	}
}