<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Purchase;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Create extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'name' => 'required|string',
        'description' => 'nullable|string'
    ];

    protected $category;

     /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        return true;
    }

    /**
     */
    public function create() {
        return Smart\User::transaction(function(){
            $now = \Carbon\Carbon::now();
            $this->category = new Smart\Category();
            $this->category->name = $this->validatorData->get('name');
            $this->category->description = $this->validatorData->get('description');
            $this->category->save();
		});
    }

    

}