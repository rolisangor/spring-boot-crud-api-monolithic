import {useEffect, useState} from "react";
import styled from "styled-components";
import { MdDeleteForever } from "react-icons/md";
import { MdBorderColor } from "react-icons/md";
import { MdArrowBack } from "react-icons/md";
import { MdArrowForward } from "react-icons/md";

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
`

const Table = styled.div`
 
`

const Row = styled.div`
  display: flex;
  background-color: ${props => (props.index % 2) === 0 ? '#4C5053': '#3F4144'};
`

const TableHeader = styled(Row)`
  background-color: #2B2E2F;
  padding: 20px 0;
  border-radius: 5px 5px 0 0;
  color: #ACB4BB;
  font-family: "Book Antiqua", sans-serif;
  font-weight: bold;
`

const TableFooter = styled(Row)`
  background-color: #2B2E2F;
  padding: 15px 20px;
  border-radius: 0 0 5px 5px;
  color: #ACB4BB;
  font-family: "Book Antiqua", sans-serif;
  font-weight: bold;
  justify-content: end;
`

const Column = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 10px 5px;
  width: ${props => props.width || "120px"};
  border: ${props => props.border && '1px solid red'};
  overflow: hidden;
  text-overflow: ellipsis;
`

const Avatar = styled.img`
  border-radius: 50px;
  background-color: bisque;
`

const Button = styled.button`
  font-size: 25px;
  color: ${props => props.delete ? '#BB515F': '#33BB8E'} ;
  cursor: pointer;
  &:hover{
    color: ${props => props.delete ? '#A04551': '#268B69'};
  }
`

const ButtonPagination = styled(Button)`
  color: #33BB8E;
  font-size: 30px;
  margin: 0 10px;
  align-items: end;
  &:hover{
    color: #268B69;
  }
`

const Crud = () => {

    const token = sessionStorage.getItem("access_token")
    const [users, setUsers] = useState(null)
    const [page, setPage] = useState(0)

    const handleEdit = () => {
        console.log("edit")
    }

    const handleDelete = () => {
        console.log("delete")
    }

    const nextPage = () => {
        console.log(page)
        setPage(page + 1) //TODO: fix pagination
        fetchData()
        console.log(page)
    }

    const fetchData = () => {
        fetch(`http://localhost:8080/api/user?page=${page}&size=10`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
            .then(async (response) => {
                const data = await response.json();
                setUsers(data)
            }).catch((err) => {
            console.log(err);
        })
    }

    useEffect(() => {
        fetchData()
    }, [])

    return (
        <Wrapper>
            <Table>
                <TableHeader>
                    <Column width='70px'>Avatar</Column>
                    <Column width='40px'>Id</Column>
                    <Column>First Name</Column>
                    <Column>Last Name</Column>
                    <Column width='200px'>Email</Column>
                    <Column>Gender</Column>
                    <Column>Company</Column>
                    <Column>Job Title</Column>
                    <Column>Role</Column>
                    <Column width='70px'>Edit</Column>
                    <Column width='70px'>Delete</Column>
                </TableHeader>
                {users && users.map((user, index) => (
                    <Row index={index} key={index}>
                        <Column width='70px'><Avatar src={user.avatar} alt="avatar"/></Column>
                        <Column width='40px'>{user.id}</Column>
                        <Column>{user.firstName}</Column>
                        <Column>{user.lastName}</Column>
                        <Column width='200px'>{user.email}</Column>
                        <Column>{user.gender}</Column>
                        <Column>{user.company}</Column>
                        <Column>{user.jobTitle}</Column>
                        <Column>{user.role.name}</Column>
                        <Column width='70px'>
                            <Button onClick={handleEdit}>
                                <MdBorderColor/>
                            </Button>
                        </Column>
                        <Column width='70px'>
                            <Button delete onClick={handleDelete}>
                                <MdDeleteForever/>
                            </Button>
                        </Column>
                    </Row>
                ))}
                <TableFooter>
                    <ButtonPagination ><MdArrowBack/></ButtonPagination>
                    <ButtonPagination onClick={nextPage}><MdArrowForward/></ButtonPagination>
                </TableFooter>
            </Table>
        </Wrapper>
    )
};

export default Crud