<?php
/*-------------------------------------------------------------------------
 * /customer
 *-------------------------------------------------------------------------
 */
$router->get('/data', [
	'as' => '/data',
	'uses' => 'CustomerController@data'
]);

$router->post('/create', [
	'as' => '/create',
	'uses' => 'CustomerController@create'
]);

$router->post('/update', [
	'as' => '/update',
	'uses' => 'CustomerController@update'
]);

$router->post('/updatePosition', [
	'as' => '/updatePosition',
	'uses' => 'CustomerController@updatePosition'
]);

$router->post('/delete', [
	'as' => '/delete',
	'uses' => 'CustomerController@delete'
]);