//CreateUser.tsx
import React from "react";
import { Formik, Form, Field, ErrorMessage, FormikHelpers } from "formik";
import * as Yup from "yup";
import UserAdd from "../../assets/Images/User Add.svg";
import { useLocation, useNavigate } from "react-router-dom";
import "./CreateUser.scss";
import instance from "../utils/axiosInstance";
import axios from "axios";



const validationSchema = Yup.object({
  name: Yup.string().required("*Required  name"),
  phoneNumber: Yup.string()
    .matches(/^[0-9]*$/, "Phone number must contain only digits")
    .min(10, "Phone number must be at least 10 digits")
    .max(10,"Phone number should can be only 10 digits")
    .required("Phone number is required"),
  issuedOnDate: Yup.date().required("*Required issuedOnDate"),
  issuedTillDate: Yup.date().required("*Required issuedTillDate"),
});

function UserForm(initialValues: any, onSubmit: any  ) {
  const location = useLocation();
  const { editingUser } = location.state || {};
  const navigate = useNavigate();
  const formikRef = React.useRef<FormikHelpers<typeof initialValues> | null>(
    null
  );

  const handleSubmit = (values: any, { resetForm }: any) => {
    

   
    fetch('http://localhost:8080/api/users', {
      
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(values),
    })
      .then(async (response) => {
        if (!response.ok) {
          const errorMessage = await response.text();
          throw new Error(`Failed to create user: ${errorMessage}`);
        }
        return response.json();
      })
      .then((createdUser) => {
        console.log('User created successfully:', createdUser);
        resetForm();
        navigate('/users');
      })
      .catch((error) => {
        console.error('Error creating user:', error);
      });


      

  

      if (editingUser) {
       
        const editedUser = {
          ...editingUser,
          name: values.name,
          phoneNumber: values.phoneNumber,
          issuedOnDate: values.issuedOnDate,
          issuedTillDate: values.issuedTillDate,
        };
        fetch(`http://localhost:8080/api/users/${editingUser.id}`, {
          method: 'PUT', // Make sure it's set to PUT
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(editedUser),
        })
          .then(async (response) => {
            if (!response.ok) {
              const errorMessage = await response.text();
              throw new Error(`Failed to update user: ${errorMessage}`);
            }
            // Handle success response (e.g., show a success message)
            console.log('User updated successfully:', editedUser);
            resetForm(); // Reset the form if needed
            navigate('/users'); // Redirect to the user list page
          })
          .catch((error) => {
            // Handle error (e.g., display an error message)
            console.error('Error updating user:', error);
          });
      } else {
      }
  };
  

  return (
    <div className="user-form">
        <h2>Create User</h2>
      <Formik
        // initialValues={initialValues}
        // validationSchema={validationSchema}
        // onSubmit={handleSubmit}
        initialValues={editingUser || initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}

      >
        <Form>
          <div className="form-table">
            <div className="form-row">
              <div className="form-col">
                <label htmlFor="name">Name</label>
                <Field type="text" id="name" name="name" />
                <ErrorMessage
                  name="name"
                  component="div"
                  className="error-message"
                />
              </div>
              <div className="form-col">
                <label htmlFor="phoneNumber">Phone Number</label>
                <Field type="text" id="phoneNumber" name="phoneNumber" />
                <ErrorMessage
                  name="phoneNumber"
                  component="div"
                  className="error-message"
                />
              </div>
              <div className="form-col">
                <label htmlFor="issuedOnDate">Issued On Date</label>
                <Field type="date" id="issuedOnDate" name="issuedOnDate" />
                <ErrorMessage
                  name="issuedOnDate"
                  component="div"
                  className="error-message"
                />
              </div>
              <div className="form-col">
                <label htmlFor="issuedTillDate">Issued Till Date</label>
                <Field type="date" id="issuedTillDate" name="issuedTillDate" />
                <ErrorMessage
                  name="issuedTillDate"
                  component="div"
                  className="error-message"
                />
              </div>
            
              <button className="add-user-button" type="submit">
              <img src={UserAdd} alt={'Submit'} />
              </button>
            </div>
          </div>
        </Form>
      </Formik>
    </div>
  );
}

export default UserForm;



