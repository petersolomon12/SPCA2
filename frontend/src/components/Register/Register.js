import { useRef, useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import { Link } from "react-router-dom"
import { useNavigate } from "react-router";
import Select from 'react-select';


import axios from "../../api/axios";

import { Form, ToastBody, ToastHeader } from "react-bootstrap";

const REGISTER_URL = '/addUser';



const Register = () => {

    const errRef = useRef();
    const [firstname, setFirstName] = useState('');
    const [lastname, setLastName] = useState('');
    const [password, setPwd] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState("");
    const [address, setAddress] = useState("");
    const [userType, setUserType] = useState('');
    const history = useHistory();
    const [isClearable, setIsClearable] = useState(true);
    const data = ['MASTERCARD', "VISA"];



    const [errMsg, setErrMsg] = useState('');


    const handleSubmit = async (e) => {
        if (userType == "") {
            setUserType('Musician');
        }
        const data = JSON.stringify({ firstname, lastname, email, phone, password, userType, address, paymentType })
        e.preventDefault();
        // if button enabled with JS hack

        try {
            const response = await axios.post(REGISTER_URL, data,
                {
                    headers: { 'Content-Type': 'application/json' },
                    "Accept": 'application/json',
                    withCredentials: true
                }).then((result) => {
                    const response = result.data;
                    if (response.userType == "Musician") {
                        console.log('Here')
                    } else {
                        console.log('Not here')
                    }
                    history.push("/login")
                })

            setFirstName('');
            setLastName('');
            setEmail('');
            setPwd('');
        } catch (err) {
            if (!err?.response) {
                setErrMsg('No Server Response');
            } else if (err.response?.status === 500) {
                setErrMsg('User Already Registered, Sign in Below');
            } else {
                setErrMsg('Registration Failed')
            }
            errRef.current.focus();
        }
    }

    const [paymentType, setPaymentType] = useState(null);

    return (
        <>

            <div class=" flex-wrap w-full">


                <div class="flex pb-4 flex-col py-10 w-full mt-52">

                    <div class="flex flex-col justify-center items-center">

                        <form class="flex flex-col justify-center items-center pr-7 md:pt-8" onSubmit={handleSubmit}>


                            <div class=" flex-col w-1/2  mb-4">
                                <div class="flex relative ">
                                    <lable className="text-black">  ADMIN

                                        <input type="radio"
                                            name="type"
                                            value="ADMIN"
                                            onClick={(e) => setUserType(e.target.value)} />

                                    </lable>  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;

                                    <lable className="text-black">  CUSTOMER

                                        <input type="radio"
                                            name="type"
                                            value="CUSTOMER"
                                            onClick={(e) => setUserType(e.target.value)} />

                                    </lable>

                                </div>
                            </div>

                            <div class=" flex-col w-1/2  mb-4">
                                <div class="flex relative ">



                                    <input type="text" id="design-login-email" required onChange={(e) => setFirstName(e.target.value)} class=" flex-1 appearance-none border border-gray-300 mr-3 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" placeholder="First Name" />

                                    <input type="text" id="design-login-email" required onChange={(e) => setLastName(e.target.value)} class=" flex-1 appearance-none border border-gray-300 mr-3 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" placeholder="Second Name" />
                                    <input type="password" id="design-login-email" required onChange={(e) => setPwd(e.target.value)} class=" flex-1 appearance-none border border-gray-300 mr-3 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" placeholder="Password" />


                                </div>

                            </div>


                            <div class=" flex-col w-1/2 mb-4">
                                <div class="flex relative ">


                                    <input type="text" id="design-login-email" required onChange={(e) => setEmail(e.target.value)} class=" flex-1 appearance-none border border-gray-300 mr-3 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" placeholder="Email Address" />
                                    <input type="text" id="design-login-email" required onChange={(e) => setAddress(e.target.value)} class=" flex-1 appearance-none border border-gray-300 mr-3 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent" placeholder="Address" />
                                    <input list="data" class=" flex-1 appearance-none border border-gray-300 mr-3 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent"
                                        onChange={(e) => setPaymentType(e.target.value)} placeholder=" Payment Method" />
                                    <datalist id="data">

                                        {data.map((op) => <option>{op}</option>)}
                                    </datalist><br /><br />

                                </div>

                            </div>



                            <div class="pl-14">
                                <button type="submit" class="px-4 py-2 text-base font-semibold text-center text-white transition duration-200 ease-in bg-black shadow-md hover:text-black hover:bg-white focus:outline-none focus:ring-2">
                                    <span class="w-full">
                                        Submit
                                    </span>
                                </button>
                            </div>
                            <p ref={errRef} className={errMsg ? "text-xl text-black rounded-lg p-1 relative" : "absolute"} aria-live="assertive">{errMsg}</p>

                        </form>
                        <div class="pt-12 pb-12 pr-10 text-black text-center">
                            <p>
                                Have an account?
                                <a href="/login" style={{ color: 'black' }} className="text-black font-semibold underline">  Login here.</a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );


}


export default Register