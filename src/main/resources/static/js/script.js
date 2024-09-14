$(function(){

// User Register validation

	var $userRegister=$("#userRegister");

	$userRegister.validate({
		
		rules:{
			name:{
				required:true,
				lettersonly:true
			}
			,
			email: {
				required: true,
				space: true,
				email: true
			},
			mobileNumber: {
				required: true,
				space: true,
				numericOnly: true,
				minlength: 10,
				maxlength: 12

			},
			password: {
				required: true,
				space: true

			},
			confirmpassword: {
				required: true,
				space: true,
				equalTo: '#pass'

			},
			address: {
				required: true,
				all: true

			},

			city: {
				required: true,
				space: true

			},
			state: {
				required: true,


			},
			pincode: {
				required: true,
				space: true,
				numericOnly: true

			}, img: {
				required: true,
			}
			
		},
		messages:{
			name:{
				required:'name required',
				lettersonly:'invalid name'
			},
			email: {
				required: 'email name must be required',
				space: 'space not allowed',
				email: 'Invalid email'
			},
			mobileNumber: {
				required: 'mob no. must be required',
				space: 'space not allowed',
				numericOnly: 'invalid mob no',
				minlength: 'min 10 digit',
				maxlength: 'max 12 digit'
			},

			password: {
				required: 'password must be required',
				space: 'space not allowed'

			},
			confirmpassword: {
				required: 'confirm password must be required',
				space: 'space not allowed',
				equalTo: 'password mismatch'

			},
			address: {
				required: 'address must be required',
				all: 'invalid'

			},

			city: {
				required: 'city must be required',
				space: 'space not allowed'

			},
			state: {
				required: 'state must be required',
				space: 'space not allowed'

			},
			pincode: {
				required: 'pincode must be required',
				space: 'space not allowed',
				numericOnly: 'invalid pincode'

			},
			img: {
				required: 'image required',
			}
		}
	})
	
	
// Orders Validation

var $orders=$("#orders");

$orders.validate({
		rules:{
			firstName:{
				required:true,
				lettersonly:true
			},
			lastName:{
				required:true,
				lettersonly:true
			}
			,
			email: {
				required: true,
				space: true,
				email: true
			},
			mobileNo: {
				required: true,
				space: true,
				numericOnly: true,
				minlength: 10,
				maxlength: 12

			},
			address: {
				required: true,
				all: true

			},

			city: {
				required: true,
				space: true

			},
			state: {
				required: true,


			},
			pincode: {
				required: true,
				space: true,
				numericOnly: true

			},
			paymentType:{
			required: true
			}
		},
		messages:{
			firstName:{
				required:'first required',
				lettersonly:'invalid name'
			},
			lastName:{
				required:'last name required',
				lettersonly:'invalid name'
			},
			email: {
				required: 'email name must be required',
				space: 'space not allowed',
				email: 'Invalid email'
			},
			mobileNo: {
				required: 'mob no must be required',
				space: 'space not allowed',
				numericOnly: 'invalid mob no',
				minlength: 'min 10 digit',
				maxlength: 'max 12 digit'
			}
		   ,
			address: {
				required: 'address must be required',
				all: 'invalid'

			},

			city: {
				required: 'city must be required',
				space: 'space not allowed'

			},
			state: {
				required: 'state must be required',
				space: 'space not allowed'

			},
			pincode: {
				required: 'pincode must be required',
				space: 'space not allowed',
				numericOnly: 'invalid pincode'

			},
			paymentType:{
			required: 'select payment type'
			}
		}	
})

// Reset Password Validation

var $resetPassword=$("#resetPassword");

$resetPassword.validate({
		
		rules:{
			password: {
				required: true,
				space: true

			},
			confirmPassword: {
				required: true,
				space: true,
				equalTo: '#pass'

			}
		},
		messages:{
		   password: {
				required: 'password must be required',
				space: 'space not allowed'

			},
			confirmPassword: {
				required: 'confirm password must be required',
				space: 'space not allowed',
				equalTo: 'password mismatch'

			}
		}	
})


//add-product

var $productForm = $("form[action='/admin/saveProduct']");

   $productForm.validate({
       rules: {
           title: {
               required: true,
               lettersonly: true
           },
           description: {
               required: true,
               all: true
           },
           category: {
               required: true
           },
           isActive: {
               required: true
           },
           price: {
               required: true,
               number: true,
               min: 0
           },
           stock: {
               required: true,
               number: true,
               min: 0
           },
           file: {
			required: true,
               extension: "jpg|jpeg|png|gif"
           }
       },
       messages: {
           title: {
               required: 'Title is required',
               lettersonly: 'Invalid title'
           },
           description: {
               required: 'Description is required',
               all: 'Invalid description'
           },
           category: {
               required: 'Category is required'
           },
           isActive: {
               required: 'Status is required'
           },
           price: {
               required: 'Price is required',
               number: 'Invalid price',
               min: 'Price must be a positive number'
           },
           stock: {
               required: 'Stock is required',
               number: 'Invalid stock',
               min: 'Stock must be a positive number'
           },
           file: {
               extension: 'Only image files are allowed (jpg, jpeg, png, gif)',
			   required: 'image required'
           }
       }
   })


   //add-category
   var $categoryForm = $("form[action='/admin/saveCategory']");

      $categoryForm.validate({
          rules: {
              name: {
                  required: true,
                  lettersonly: true
              },
              isActive: {
                  required: true
              },
              file: {
				required: true,
                  extension: "jpg|jpeg|png|gif"
              }
          },
          messages: {
              name: {
                  required: 'Category name is required',
                  lettersonly: 'Invalid category name'
              },
              isActive: {
                  required: 'Status is required'
              },
              file: {
                  extension: 'Only image files are allowed (jpg, jpeg, png, gif)',
				  required: 'image required'
              }
          }
      })
	  
	  
	  //add admin
	  
	  var $adminForm = $("form[action='/admin/save-admin']");

	     $adminForm.validate({
	         rules: {
	             name: {
	                 required: true,
	                 lettersonly: true
	             },
	             mobileNumber: {
	                 required: true,
	                 digits: true,
	                 minlength: 10,
	                 maxlength: 12
	             },
	             email: {
	                 required: true,
	                 email: true
	             },
	             address: {
	                 required: true,
	                 all: true
	             },
	             city: {
	                 required: true,
	                 lettersonly: true
	             },
	             state: {
	                 required: true,
	                 lettersonly: true
	             },
	             pincode: {
	                 required: true,
	                 digits: true,
	                 minlength: 6,
	                 maxlength: 6
	             },
	             password: {
	                 required: true,
	                 minlength: 6
	             },
	             cpassword: {
	                 required: true,
	                 minlength: 6,
	                 equalTo: '[name="password"]'
	             },
	             img: {
					required: true,
	                 extension: "jpg|jpeg|png|gif"
	             }
	         },
	         messages: {
	             name: {
	                 required: 'Full Name is required',
	                 lettersonly: 'Only letters and spaces allowed'
	             },
	             mobileNumber: {
	                 required: 'Mobile Number is required',
	                 digits: 'Only digits allowed',
	                 minlength: 'Must be at least 10 digits',
	                 maxlength: 'Cannot exceed 12 digits'
	             },
	             email: {
	                 required: 'Email is required',
	                 email: 'Invalid email address'
	             },
	             address: {
	                 required: 'Address is required',
	                 all: 'Invalid address'
	             },
	             city: {
	                 required: 'City is required',
	                 lettersonly: 'Only letters and spaces allowed'
	             },
	             state: {
	                 required: 'State is required',
	                 lettersonly: 'Only letters and spaces allowed'
	             },
	             pincode: {
	                 required: 'Pincode is required',
	                 digits: 'Only digits allowed',
	                 minlength: 'Must be exactly 6 digits',
	                 maxlength: 'Must be exactly 6 digits'
	             },
	             password: {
	                 required: 'Password is required',
	                 minlength: 'Password must be at least 6 characters'
	             },
	             cpassword: {
	                 required: 'Confirm Password is required',
	                 minlength: 'Password must be at least 6 characters',
	                 equalTo: 'Passwords do not match'
	             },
	             img: {
	                 extension: 'Only image files are allowed (jpg, jpeg, png, gif)',
					 required: 'image required'
	             }
	         }
	     })
	
	
	
})



jQuery.validator.addMethod('lettersonly', function(value, element) {
		return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
	});
	
		jQuery.validator.addMethod('space', function(value, element) {
		return /^[^-\s]+$/.test(value);
	});

	jQuery.validator.addMethod('all', function(value, element) {
		return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
	});


	jQuery.validator.addMethod('numericOnly', function(value, element) {
		return /^[0-9]+$/.test(value);
	});
	
	jQuery.validator.addMethod('extension', function(value, element, param) {
	    var allowedExtensions = param.split('|');
	    var fileExtension = value.split('.').pop().toLowerCase();
	    return this.optional(element) || allowedExtensions.includes(fileExtension);
	});
