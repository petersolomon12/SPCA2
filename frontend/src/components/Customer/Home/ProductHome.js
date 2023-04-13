import axios from "../../../api/axios";
import React, { useEffect, useMemo, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TableSortLabel, Paper } from '@material-ui/core';
import { useTable, useFilters, useSortBy } from 'react-table';
import MusNav from "../Nav/MusNav";

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

const ProductHome = () => {

  const allProducts = '/allProducts';
  const addItems = '/addItems';
  const addReview = '/addReview';
  const [data, setData] = useState([]);
  const [reviews, setReviews] = useState([]);
  const userObject = localStorage.getItem("User");
  var userObj = JSON.parse(userObject);
  const uuid = userObj.id;
  const [sortOrder, setSortOrder] = useState('asc');
  const [sortColumn, setSortColumn] = useState(null);
  const [showModal1, setShowModal1] = useState(false);
  const [showModal2, setShowModal2] = useState(false);
  const [proUuid, setUuid] = useState('')
  const [comment, setComment] = useState('')
  const [rating, setRating] = useState('')





  useEffect(() => {

    // if button enabled with JS hack

    try {

      axios.post(allProducts,
        {
          headers: { 'Content-Type': 'application/json' },
          "Accept": 'application/json',
          withCredentials: true
        }).then((res) => {
          setData(res.data);
        })

    } catch (err) {
      console.log(err)

    }
  }, []);


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
      },
      {
        Header: 'Price',
        accessor: 'price',
      },
      {
        Header: 'Add To Cart',
        accessor: 'id',
        Cell: ({ row }) => (
          <button onClick={() => handleSubmit(row.original.id)}>
            Add to cart
          </button>
        ),
      },
      {
        Header: 'Add Review',
        accessor: 'review',
        Cell: ({ row }) => (
          <button onClick={() => toggleModal1(row.original.id)}>
            Add Review
          </button>
        ),
      },
      {
        Header: 'View Review',
        accessor: 'productReviews',
        Cell: ({ row }) => (
          <button onClick={() => toggleModal2(row.original.reviews)}>
            View Reviews
          </button>
        ),
      },
    ],
    []
  );

  const toggleModal1 = (id) => {
    setShowModal1(!showModal1);
    setUuid(id);
  };
  
  const toggleModal2 = (reviews) => {
    setShowModal2(!showModal2);
    setReviews(reviews);
  };

  


  const handleSubmit = async (id) => {


    const productUuid = id;

    try {
      const response = await axios.post(addItems,
        JSON.stringify({ uuid, productUuid }),
        {
          headers: { 'Content-Type': 'application/json' },
          "Accept": 'application/json',
          withCredentials: true
        }).then((result) => {
          alert('Succesfully Added to Cart')
        })
    } catch (err) {
    }
  }
  const handleReviewSubmit = async (id) => {

    const uuid = proUuid;

    try {
      const response = await axios.post(addReview,
        JSON.stringify({uuid, comment, rating}),
        {
          headers: { 'Content-Type': 'application/json' },
          "Accept": 'application/json',
          withCredentials: true
        }).then((result) => {
          alert('Succesfully Added to Cart')
          window.location.reload()
        })

      //clear state and controlled inputs
      //need value attrib on inputs for this
    } catch (err) {
    }
  }

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
      initialState: { sortBy: [{ id: 'price', desc: false }] },
    },
    useFilters,
    useSortBy
  );

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
                    <th
                      {...column.getHeaderProps(column.getSortByToggleProps())}
                      className={`${column.isSorted
                        ? column.isSortedDesc
                          ? 'sorted-desc'
                          : 'sorted-asc'
                        : ''
                        }`}
                    >
                      {column.render('Header')}
                      {column.isSorted ? (
                        column.isSortedDesc ? (
                          <span className="ml-2">▼</span>
                        ) : (
                          <span className="ml-2">▲</span>
                        )
                      ) : (
                        ''
                      )}
                    </th>

                  ))}
                </tr>
              ))}
            </thead>
            <tbody class="divide-y divide-gray-100 border-t border-gray-100" {...getTableBodyProps()}>
              {rows.map((row) => {
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
            {showModal1 ? (
              <div id="small-modal" tabindex="-1" style={{ paddingLeft: "19.5rem" }} class="fixed top-0 left-0 right-0 z-50 w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-modal md:h-full">
                <div class="relative w-full h-full max-w-md md:h-auto">
                  <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                    <div class="flex items-center justify-between p-5 border-b rounded-t dark:border-gray-600">
                      <h3 class="text-xl font-medium text-gray-900 dark:text-white">
                        Edit Profile
                      </h3>


                      <button onClick={toggleModal1} style={{ paddingLeft: "13.5rem" }} type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="small-modal">
                        <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                        <span class="sr-only">Close modal</span>
                      </button>
                    </div>

                    <div className="pl-10">
                      <div className="pb-10">
                        <div className="flex" style={{ alignItems: "flex-start", flexDirection: "column" }}>
                          <label style={{ margin: "0 0 0.2rem", fontSize: "0.8rem", color: "rgba(0,0,0,0.6)" }} className="font-normal">
                            Comment*
                          </label>
                          <input style={{ boxShadow: "inset 0 0 0 1px" }} id="" value={comment} onChange={(e) => setComment(e.target.value)} placeholder="" required type="text" />
                        </div>
                        <div className="rating">
                          <input type="radio" value={1} onChange={(e) => setRating(e.target.value)} name="rating-1" className="mask mask-star" />
                          <input type="radio" value={2} onChange={(e) => setRating(e.target.value)} name="rating-1" className="mask mask-star" />
                          <input type="radio" value={3} onChange={(e) => setRating(e.target.value)} name="rating-1" className="mask mask-star" />
                          <input type="radio" value={4} onChange={(e) => setRating(e.target.value)} name="rating-1" className="mask mask-star" />
                          <input type="radio" value={5} onChange={(e) => setRating(e.target.value)} name="rating-1" className="mask mask-star" />
                        </div>
                      </div>



                      <button onClick={handleReviewSubmit} data-modal-hide="small-modal" type="button" class="mb-4 text-white bg-gray-400 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Save</button>

                    </div>

                    <div className="pl-10 mb-4">

                    </div>
                  </div>
                </div>
              </div>

            ) : null}

            {showModal2 ? (
                <div id="small-modal" tabindex="-1" style={{ paddingLeft: "19.5rem" }} class="fixed top-0 left-0 right-0 z-50 w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-modal md:h-full">
                <div style={{maxWidth:"54rem"}} class="relative w-full h-full md:h-auto">
                    <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                        <div class="flex items-center justify-between p-5 border-b rounded-t dark:border-gray-600">
                            <h3 class="text-xl font-medium text-gray-900 dark:text-white">
                                Product Reviews
                            </h3>

                            <button onClick={toggleModal2} style={{ paddingLeft: "13.5rem" }} type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="small-modal">
                                <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                                <span class="sr-only">Close modal</span>
                            </button>
                        </div>

                        <div className="pl-10">
                            <table class="w-full border-collapse bg-white text-left text-sm text-gray-500">
                                <thead class="bg-gray-50">
                                    <tr>
                                        <th scope="col" class="px-6 py-4 font-medium text-gray-900">Comment</th>
                                        <th scope="col" class="px-6 py-4 font-medium text-gray-900">Rating</th>
                                    </tr>
                                </thead>
                                <tbody class="divide-y divide-s-100 border-t border-gray-100">
                                {reviews && reviews.map((review) => (

                                        <tr class="hover:bg-gray-50">
                                            <th class="flex gap-3 px-6 py-4 font-normal text-gray-900">
                                                <div class="text-sm">
                                                    <div class="font-medium text-gray-700">{review.comment}</div>
                                                    <div class="text-gray-400">{review.rating}</div>
                                                </div>
                                            </th>
                                            <td class="px-6 py-4">
                                                <span
                                                    class="inline-flex items-center gap-1 rounded-full bg-green-50 px-2 py-1 text-xs font-semibold text-green-600"
                                                >
                                                    <span class="h-1.5 w-1.5 rounded-full bg-green-600"></span>
                                                    {review.rating}
                                                </span>
                                            </td>
                                        </tr>
                                    ))}

                                </tbody>
                            </table>


                        </div>

                        <div className="pl-10 mb-4">

                        </div>
                    </div>
                </div>
            </div>

            ) : null}
          </table>
          
        </div>
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
  <strong class="font-bold">20% Discount if you buy 3 or more things!</strong>
  <span class="absolute top-0 bottom-0 right-0 px-4 py-3">
    <svg class="fill-current h-6 w-6 text-red-500" role="button" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><title>Close</title><path d="M14.348 14.849a1.2 1.2 0 0 1-1.697 0L10 11.819l-2.651 3.029a1.2 1.2 0 1 1-1.697-1.697l2.758-3.15-2.759-3.152a1.2 1.2 0 1 1 1.697-1.697L10 8.183l2.651-3.031a1.2 1.2 0 1 1 1.697 1.697l-2.758 3.152 2.758 3.15a1.2 1.2 0 0 1 0 1.698z"/></svg>
  </span>
</div>
      </div>
    </>
  );
}

export default ProductHome;