<?php
/*-------------------------------------------------------------------------
 * /category
 *-------------------------------------------------------------------------
 */
$router->get('/data', [
	'as' => '/data',
	'uses' => 'CategoryController@data'
]);

$router->post('/create', [
	'as' => '/create',
	'uses' => 'CategoryController@create'
]);

$router->post('/update', [
	'as' => '/update',
	'uses' => 'CategoryController@update'
]);

$router->post('/updatePosition', [
	'as' => '/updatePosition',
	'uses' => 'CategoryController@updatePosition'
]);

$router->post('/delete', [
	'as' => '/delete',
	'uses' => 'CategoryController@delete'
]);