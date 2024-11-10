<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Http\Request;
use App\Http\Bundle\Service\Api\Models;

/**
 */
class UserController extends AbstractController
{
    /*-------------------------------------------------------------------------
     * /user/account
     *-------------------------------------------------------------------------
     */
    public function account(Request $request)
    {
        $account = new Models\User\Account($request);
        if (!$account->validate()) {
            return $this->jsonError()
                ->params($account->getValidator()->errors())
                ->build();
        }
        return $this->jsonData($account->response())
            ->build();
    }

    
    /*-------------------------------------------------------------------------
     * /user/update
     *-------------------------------------------------------------------------
     */
    public function update(Request $request)
    {
        $update = new Models\User\Update($request);
        if (!$update->validate()) {
            return $this->jsonError()
                ->params($update->getValidator()->errors())
                ->build();
        }
        $update->update();
        return $this->jsonData($update->response())
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /user/status => update new user status
     *-------------------------------------------------------------------------
     */
    public function status(Request $request)
    {
        $status = new Models\User\Status($request);
        if (!$status->validate()) {
            return $this->jsonError()
                ->params($status->getValidator()->errors())
                ->build();
        }
        $status->update();
        return $this->jsonSuccess()
			->build();
    }


    /*-------------------------------------------------------------------------
     * /user/password => update password
     *-------------------------------------------------------------------------
     */
    public function password(Request $request)
    {
        $password = new Models\User\Password($request);
        if (!$password->validate()) {
            return $this->jsonError()
                ->params($password->getValidator()->errors())
                ->build();
        }
        $password->update();
        return $this->jsonSuccess()
			->build();
    }

   
    /*-------------------------------------------------------------------------
     * /user/photo
     *-------------------------------------------------------------------------
     */
    public function photo(Request $request)
    {
        $photo = new Models\User\Photo($request);
        if (!$photo->validate()) {
            return $this->jsonError()
                ->params($photo->getValidator()->errors())
                ->build();
        }
        $photo->upload();
        return $this->jsonData($photo->response())
            ->build();
    }

    /**
	 */
	public function uploadMedia(Request $request){
		$uploadMedia = new Models\User\UploadMedia($request);		
		if (!$uploadMedia->validate()) {
			return $this->jsonError()
				->params($uploadMedia->getValidator()->errors())
				->build();
		}
		$uploadMedia->upload();
		return $this->jsonData($uploadMedia->response())->build();
	}
}