import axios from "../../../api/axios";
import React, { useEffect, useMemo, useState } from 'react';
import { useTable, useFilters } from 'react-table';
import MusNav from "../Nav/MusNav";
import { Redirect, useHistory } from "react-router-dom";


const CategoryFilter = ({ column: { filterValue, setFilter } }) => {
    return (
      <input
        value={filterValue || ''}
        onChange={(e) => {
          setFilter(e.target.value || undefined);
        }}
      />
    );
  };

  const DefaultFilter = ({ column: { filterValue, setFilter } }) => {
    return (
      <input
        value={filterValue || ''}
        onChange={(e) => {
          setFilter(e.target.value || undefined);
        }}
      />
    );
  };

const ShoppingCart = () => {
    const getUserCart = '/getUserCart';
    const purchaseItem = '/purchaseItem';
    const [data, setData] = useState([]);
    const [cart, setCart] = useState([]);
    const userObject = localStorage.getItem("User");
    var userObj = JSON.parse(userObject);
    const uuid = userObj.id;
    const history = useHistory();



    const columns = useMemo(
        () => [
          {
            Header: 'Name',
            accessor: 'name',
          },
          {
            Header: 'Category',
            accessor: 'category',
            Filter: CategoryFilter,
          },
          {
            Header: 'Manufacturer',
            accessor: 'manufacturer',
          }
        ],
        []
      );

      const handleSubmit = async () => {
    
        const id = cart.id;

      
          try{
              const response = await axios.post(purchaseItem,
                  JSON.stringify({uuid, id}),
                  {
                      headers: { 'Content-Type': 'application/json' },
                      "Accept" : 'application/json',
                      withCredentials: true
                  }).then((result) => {
                  alert('Succesfully Paid')
                  history.push("/productHome")
                })
      
              //clear state and controlled inputs
              //need value attrib on inputs for this
          } catch (err) {
              }
      }


    useEffect(() => {
       
        const data = JSON.stringify({ uuid });
        // if button enabled with JS hack

        try {

            axios.post(getUserCart, data,
                {
                    headers: { 'Content-Type': 'application/json' },
                    "Accept": 'application/json',
                    withCredentials: true
                }).then((res) => {
                    setData(res.data.product);
                    setCart(res.data);
                })

        } catch (err) {
            console.log(err)

        }
      }, []);

  const defaultColumn = useMemo(
    () => ({
      Filter: DefaultFilter,
    }),
    []
  );

  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
    setFilter,
  } = useTable(
    {
      columns,
      data,
      defaultColumn,
    },
    useFilters
  );

  const calculateTotalPrice = (items) => {
    const total = items.reduce((acc, item) => acc + item.price, 0);
    if (items.length >= 3) {
      const discount = total * 0.2;
      return total - discount;
    } else {
      return total;
    }
  }
  
  const totalPrice = calculateTotalPrice(data);



  return ( 
    <>
    <MusNav />

    <div class="overflow-hidden rounded-lg border border-gray-200 shadow-md m-5 ml-72">
    <div className=" ml-8">
    <input
    className="mb-6"
      type="text"
      placeholder="Search by name"
      onChange={(e) => {
        setFilter('name', e.target.value);
      }}
    />
    <input
      className="mb-6"
      type="text"
      placeholder="Search by category"
      onChange={(e) => {
        setFilter('category', e.target.value);
      }}
    />
    <table class="w-full border-collapse bg-white text-left text-sm text-gray-500" {...getTableProps()}>
      <thead class="bg-gray-50">
        {headerGroups.map((headerGroup) => (
          <tr {...headerGroup.getHeaderGroupProps()}>
            {headerGroup.headers.map((column) => (
              <th class="px-6 py-4 font-medium text-gray-900" {...column.getHeaderProps()}>{column.render('Header')}</th>
            ))}
          </tr>
        ))}
      </thead>
      <tbody class="divide-y divide-gray-100 border-t border-gray-100" {...getTableBodyProps()}>
        {rows && rows.map((row) => {
          prepareRow(row);
          return (
            <tr class="hover:bg-gray-50" {...row.getRowProps()}>
              {row.cells.map((cell) => (
                <td class="px-6 py-4" {...cell.getCellProps()}>{cell.render('Cell')}</td>
              ))}
            </tr>
          );
        })}
      </tbody>
    </table>
    
    </div>
    <a style={{width:"15rem"}} href="#" onClick={handleSubmit} class="inline-flex items-center ml-64 px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
        TOTAL  {totalPrice}
    </a>
    <a style={{width:"15rem"}} href="#" onClick={handleSubmit} class="inline-flex items-center ml-64 px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
        PAY NOW 
    </a>
    </div>
  </>
 );  
}
 
export default ShoppingCart;