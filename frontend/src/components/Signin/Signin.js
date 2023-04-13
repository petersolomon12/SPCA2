import { useState, useRef} from "react";
import { Button } from 'react-bootstrap';
import {Link} from "react-router-dom"
import { Redirect, useHistory } from "react-router-dom";
import axios from "../../api/axios";

import {Form, ToastBody, ToastHeader} from "react-bootstrap";




const REGISTER_URL = '/userLogin';



const Signin = () => {

    const errRef = useRef();
    const history = useHistory();

    const [email, setEmailAddress] = useState('');
    const [password, setPassword] = useState('');


    const [errMsg, setErrMsg] = useState('');

    const handleSubmit = async (e) => {
        const data = JSON.stringify({email, password})
        e.preventDefault();
        // if button enabled with JS hack
   
        try{

            await axios.post(REGISTER_URL,data,
                {
                    headers: { 'Content-Type': 'application/json' },
                    "Accept" : 'application/json',
                    withCredentials: true
                }).then((result) => {
                    const response = result.data;
                    localStorage.setItem('User', JSON.stringify(response))
                        if(response.userType=="ADMIN"){
                           history.push("/customers");
                        }else {
                            history.push("/productHome")
                        }
                        // history.push("/home")
                })

        } catch (err) {
            if (!err?.response) {
                setErrMsg('No Server Response');
            } else if (err.response?.status === 500) {
                setErrMsg('Email Or Password invalid');
            } else {
                setErrMsg('Registration Failed')
            }
            errRef.current.focus();
        }
}
    

    return ( 

        <>
      
        <div class=" flex-wrap w-full">
    
       
   
       <div class="flex pb-8 flex-col py-14 w-full mt-52">
           
           <div class="flex flex-col justify-center items-center">
        
       
               <form class="flex flex-col justify-center items-center pr-7 md:pt-8" onSubmit={handleSubmit}>
            
       
               <div class=" flex-col mb-4">
               <div class="flex relative ">
       
       
                   <input type="text" id="design-login-email" required onChange={(e) => setEmailAddress(e.target.value)} class=" flex-1 appearance-none border border-gray-300 mr-3 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" placeholder="Email Address"/>
       
           
                   </div>
       
                   </div>

                   <div class=" flex-col mb-4">
               <div class="flex relative ">
       
       
                   <input type="password" id="design-login-email" required onChange={(e) => setPassword(e.target.value)} class=" flex-1 appearance-none border border-gray-300 mr-3 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" placeholder="Password"/>
       
           
                   </div>
       
                   </div>
            
                   <p ref={errRef} className={errMsg ? "text-xl text-black rounded-lg p-1 relative" : "absolute"} aria-live="assertive">{errMsg}</p>

                           <button type="submit" class="px-4 py-2 text-base font-semibold text-center text-white transition duration-200 ease-in bg-black shadow-md hover:text-black hover:bg-white focus:outline-none focus:ring-2">
                               <span class="w-full">
                                   Submit
                               </span>
                           </button>
                       </form>
                       
                       <div class="pt-4 pb-10 text-black text-center">
                           <p>
                               Don't have an account? 
                               <a href="/register" style={{color:'black'}} className="text-black font-semibold underline">  Register here.</a>
                           </p>
                       </div>
                   </div>
               </div>
           </div>
             </>

     );
}

export default Signin;