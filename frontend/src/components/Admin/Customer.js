import AdminNav from "./Nav/AdminNav";
import axios from "../../api/axios";
import { useState, useEffect } from "react";


const Customers = () => {
    const getCustomers = '/getCustomers';
    const [customers, setCustomers] = useState([]);
    const [showModal1, setShowModal1] = useState(false);
    const [selectedCustomer, setSelectedCustomer] = useState(null);

    const toggleModal1 = (customer) => {
        setSelectedCustomer(customer);
        setShowModal1(!showModal1);
    };

    useEffect(() => {

        try {

            axios.post(getCustomers,
                {
                    headers: { 'Content-Type': 'application/json' },
                    "Accept": 'application/json',
                    withCredentials: true
                }).then((result) => {
                    setCustomers(result.data);
                })

        } catch (err) {
            console.log(err)

        }

    }, []);


    return (

        <div>
            <AdminNav />

            <div class="overflow-hidden rounded-lg border border-gray-200 shadow-md m-5 ml-72">
                <table class="w-full border-collapse bg-white text-left text-sm text-gray-500">
                    <thead class="bg-gray-50">
                        <tr>
                            <th scope="col" class="px-6 py-4 font-medium text-gray-900">Name</th>
                            <th scope="col" class="px-6 py-4 font-medium text-gray-900">State</th>
                            <th scope="col" class="px-6 py-4 font-medium text-gray-900">Role</th>
                            <th scope="col" class="px-6 py-4 font-medium text-gray-900">Purchasing Experience</th>
                            <th scope="col" class="px-6 py-4 font-medium text-gray-900"></th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-100 border-t border-gray-100">
                        {customers.map((customers) => (

                            <tr class="hover:bg-gray-50">
                                <th class="flex gap-3 px-6 py-4 font-normal text-gray-900">
                                    <div class="relative h-10 w-10">
                                        <img
                                            class="h-full w-full rounded-full object-cover object-center"
                                            src="https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                                            alt=""
                                        />
                                        <span class="absolute right-0 bottom-0 h-2 w-2 rounded-full bg-green-400 ring ring-white"></span>
                                    </div>
                                    <div class="text-sm">
                                        <div class="font-medium text-gray-700">{customers.firstname} {customers.lastname}</div>
                                        <div class="text-gray-400">{customers.email}</div>
                                    </div>
                                </th>
                                <td class="px-6 py-4">
                                    <span
                                        class="inline-flex items-center gap-1 rounded-full bg-green-50 px-2 py-1 text-xs font-semibold text-green-600"
                                    >
                                        <span class="h-1.5 w-1.5 rounded-full bg-green-600"></span>
                                        Active
                                    </span>
                                </td>
                                <td class="px-6 py-4">CUSTOMER</td>
                                <td key={customers.id} onClick={() => toggleModal1(customers)} class="px-6 py-4">
                                    <div class="flex gap-2">
                                        <span
                                            class="inline-flex items-center gap-1 rounded-full bg-blue-50 px-2 py-1 text-xs font-semibold text-blue-600"
                                        >
                                            Purchasing History
                                        </span>
                                    </div>
                                </td>
                                {showModal1 && (
                            <CustomerModal
                            customer={selectedCustomer}
                            closeModal={() => setShowModal1(false)}
                            />
                           
                        )}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>

    );
}

function CustomerModal({customer, closeModal}){
    return(
            <div id="small-modal" tabindex="-1" style={{ paddingLeft: "19.5rem" }} class="fixed top-0 left-0 right-0 z-50 w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-modal md:h-full">
                <div style={{maxWidth:"54rem"}} class="relative w-full h-full md:h-auto">
                    <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                        <div class="flex items-center justify-between p-5 border-b rounded-t dark:border-gray-600">
                            <h3 class="text-xl font-medium text-gray-900 dark:text-white">
                                Purchasing History
                            </h3>

                            <button onClick={closeModal} style={{ paddingLeft: "13.5rem" }} type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="small-modal">
                                <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                                <span class="sr-only">Close modal</span>
                            </button>
                        </div>

                        <div className="pl-10">
                            <table class="w-full border-collapse bg-white text-left text-sm text-gray-500">
                                <thead class="bg-gray-50">
                                    <tr>
                                        <th scope="col" class="px-6 py-4 font-medium text-gray-900">Name</th>
                                        <th scope="col" class="px-6 py-4 font-medium text-gray-900">State</th>
                                        <th scope="col" class="px-6 py-4 font-medium text-gray-900">Role</th>
                                        <th scope="col" class="px-6 py-4 font-medium text-gray-900">Purchasing Experience</th>
                                        <th scope="col" class="px-6 py-4 font-medium text-gray-900"></th>
                                    </tr>
                                </thead>
                                <tbody class="divide-y divide-s-100 border-t border-gray-100">
                                {Array.isArray(customer.purchaseHistory) && customer.purchaseHistory.map((purchaseHistory) => (
                                    purchaseHistory.product.map((product) => (

                                        <tr class="hover:bg-gray-50">
                                            <th class="flex gap-3 px-6 py-4 font-normal text-gray-900">
                                                <div class="text-sm">
                                                    <div class="font-medium text-gray-700">{product.name}</div>
                                                    <div class="text-gray-400">{product.category}</div>
                                                </div>
                                            </th>
                                            <td class="px-6 py-4">
                                                <span
                                                    class="inline-flex items-center gap-1 rounded-full bg-green-50 px-2 py-1 text-xs font-semibold text-green-600"
                                                >
                                                    <span class="h-1.5 w-1.5 rounded-full bg-green-600"></span>
                                                    Active
                                                </span>
                                            </td>
                                            <td class="px-6 py-4">CUSTOMER</td>
                                            <td class="px-6 py-4">
                                                <div class="flex gap-2">
                                                    <span
                                                        class="inline-flex items-center gap-1 rounded-full bg-blue-50 px-2 py-1 text-xs font-semibold text-blue-600"
                                                    >
                                                        Purchasing History
                                                    </span>
                                                </div>
                                            </td>
                                        </tr>
                                    ))
                                    ))}

                                </tbody>
                            </table>


                        </div>

                        <div className="pl-10 mb-4">

                        </div>
                    </div>
                </div>
            </div>
    )
}

export default Customers;