<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Tax;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Delete extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'tax' => 'required|numeric'
    ];

    protected $tax;

     /**
	 */
	public function validate() {
		if (!parent::validate()) {
			return false;
		}
        $this->tax = Smart\Tax::select()
            ->where('tax.id', $this->validatorData->get('tax'))
            ->first();
        if (!$this->tax) {
            return false;
        }
        return true;
    }

    /**
     */
    public function delete() {
        return Smart\User::transaction(function(){
            // if ($this->tax->hasItems()) {
            //     return;
            // }
            // $this->tax->delete();
		});
    }

    

}