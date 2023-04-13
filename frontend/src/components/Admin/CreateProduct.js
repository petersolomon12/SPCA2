import AdminNav from "./Nav/AdminNav";
import {React, useEffect, useState} from "react";
import axios from "../../api/axios";
import { useNavigate } from "react-router";
import { Container, Form, InputGroup, FormControl, Row, Button, Col} from "react-bootstrap";
import { Redirect, useHistory } from "react-router-dom";

const CreateProduct = () => {
    const addProduct = '/addProduct';
    const history = useHistory();
    const [name, setName] = useState("")
    const [manufacturer, setManufacturer] = useState("")
    const [stockLevel, setStockLevel] = useState("")
    const [price, setPrice] = useState("")
    const [category, setCategory] = useState("")
    const userObject = localStorage.getItem("User");
    var userObj = JSON.parse(userObject);

    const handleSubmit = async () => {
      const uuid = userObj.id;
          try{
              const response = await axios.post(addProduct,
                JSON.stringify({ price, name, category, manufacturer, stockLevel, uuid}),
                  {
                      headers: { 'Content-Type': 'application/json' },
                      "Accept" : 'application/json',
                      withCredentials: true
                  }).then((result) => {
                  alert('Succesfully Added New Product')
                  history.pushState("/products")
                  })
            } catch (err) {
                alert(err)
              }
      }
    
    return ( 
        <div>
        <AdminNav />

        <div>
            <h3 className="text-success mt-5 p-3 text-center rounded">Add Product</h3>
          
            <form onSubmit={handleSubmit} className=" ml-80 w-2/4">
            <div class="mb-6">
                <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Product Name</label>
                <input type="text" onChange={e => setName(e.target.value)} id="email" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="name@flowbite.com" required/>
            </div>
            <div class="mb-6">
                <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Product Manufacturer</label>
                <input type="text" onChange={e => setManufacturer(e.target.value)} id="email" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="name@flowbite.com" required/>
            </div>
            <div class="mb-6">
                <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Stock Level</label>
                <input type="text" onChange={e => setStockLevel(e.target.value)} id="email" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="name@flowbite.com" required/>
            </div>
            <div class="mb-6">
                <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Category</label>
                <input type="text" onChange={e => setCategory(e.target.value)} id="email" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="name@flowbite.com" required/>
            </div>
            <div class="mb-6">
                <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Price</label>
                <input type="text" onChange={e => setPrice(e.target.value)} id="email" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="name@flowbite.com" required/>
            </div>

            <button onClick={handleSubmit} type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Add Product</button>
            </form>

        </div>
        </div>

     );
}
 
export default CreateProduct;