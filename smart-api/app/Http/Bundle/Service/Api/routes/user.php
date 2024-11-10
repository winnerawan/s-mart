<?php
/*-------------------------------------------------------------------------
 * /user
 *-------------------------------------------------------------------------
 */
$router->get('/account', [
	'as' => '/account',
	'uses' => 'UserController@account'
]);

$router->post('/photo', [
	'as' => '/photo',
	'uses' => 'UserController@photo'
]);

$router->post('/status', [
	'as' => '/status',
	'uses' => 'UserController@status'
]);

$router->post('/update', [
	'as' => '/update',
	'uses' => 'UserController@update'
]);

$router->post('/destroy', [
	'as' => '/destroy',
	'uses' => 'UserController@destroy'
]);

$router->get('/data', [
	'as' => '/data',
	'uses' => 'UserController@data'
]);

$router->get('/search', [
	'as' => '/search',
	'uses' => 'UserController@search'
]);

$router->post('/uploadMedia', [
	'as' => '/uploadMedia',
	'uses' => 'UserController@uploadMedia'
]);

$router->post('/password', [
	'as' => '/password',
	'uses' => 'UserController@password'
]);

