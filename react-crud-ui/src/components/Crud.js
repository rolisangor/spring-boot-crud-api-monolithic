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
    position: relative;
`

const Row = styled.div`
  display: flex;
  background-color: ${props => (props.index % 2) === 0 ? '#4C5053': '#3F4144'};
  min-height: 60px;
  max-height: 60px;
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
  font-size: 14px;
  width: ${props => props.width || "100px"};
  overflow: hidden;
  text-overflow: ellipsis;
`

const Avatar = styled.img`
  background-color: bisque;
  width: 50px;
  height: 50px;
  border-radius: 50%;
`

const Button = styled.button`
  font-size: 20px;
  color: ${props => props.delete ? '#BB515F': '#33BB8E'} ;
  cursor: pointer;
  &:hover{
    color: ${props => props.delete ? '#A04551': '#268B69'};
  }
`

const ButtonPagination = styled(Button)`
  color: #33BB8E;
  font-size: 25px;
  margin: 0 10px;
  align-items: end;
  &:hover{
    color: #268B69;
  }
`

const Modal = styled.div`
  width: 100%;
  height: 100%;
  position: absolute;
  background-color: rgba(25, 25, 25, .9);
  z-index: 100;
`

const ModalBody = styled.form`
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: center;
  padding: 0 30px;
`

const SectionWrapper = styled.div`
  display: flex;
`

const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 10px;
  align-items: ${props => props.center && 'center' || 'start'};
`

const Label = styled.label`
  margin-left: 3px;
  color: rgb(150, 150, 150);
  margin-bottom: 5px;
  font-size: 14px;
  font-family: Ubuntu, sans-serif;
`

const Input = styled.input`
  background-color: #3c3f41;
  padding: 15px 10px;
  border-radius: 5px;
  width: 100%;
  //margin: 15px 30px;
  position: relative;
`

const ModalButton = styled.button`
  background-color: #3c3f41;
  padding: 15px;
  border-radius: 5px;
  text-transform: uppercase;
  width: 50%;
  text-align: center;
  color: ${props => props.close && '#BB515F' || '#33BB8E'} ;
  cursor: pointer;
  margin-top: 15px;
  &:hover {
    color: ${props => props.close && '#A04551' || '#268B69'} ;
    background-color: #3F4144;
  }
`

const Crud = () => {

    const token = sessionStorage.getItem("access_token")
    const [users, setUsers] = useState(null)
    const [page, setPage] = useState(0)
    const [pageSize, setPageSize] = useState(7)
    const [showModal, setShowModal] = useState(false)

    const [modalUserId, setModalUserId] = useState('')
    const [modalJobTitle, setModalJobTitle] = useState('')
    const [modalFirstName, setModalFirstName] = useState('')
    const [modalLastName, setModalLastName] = useState('')
    const [modalEmail, setModalEmail] = useState('')
    const [modalGender, setModalGender] = useState('')
    const [modalCompany, setModalCompany] = useState('')
    const [modalAvatarUrl, setModalAvatarUrl] = useState('')

    const handleEdit = (user) => {
        setModalUserId(user.id)
        setModalJobTitle(user.jobTitle)
        setModalFirstName(user.firstName)
        setModalLastName(user.lastName)
        setModalEmail(user.email)
        setModalGender(user.gender)
        setModalCompany(user.company)
        setModalAvatarUrl(user.avatar)
        setShowModal(true)
    }

    const handleDelete = (user) => {
        const url = `http://localhost:8080/api/user/${user.id}`
        fetch(url, {
            method: 'DELETE',
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }).then(async (response) => {
            const data = await response.text()
            if (response.ok) {
                fetchData(page, pageSize)
            }else {
                throw Error("User delete error")
            }
        }).catch((err) => {
            console.log(err)
        })
    }

    const nextPage = () => {
        if (users.length < pageSize) return
        fetchData(page + 1, pageSize)
        setPage(page + 1)
    }

    const prevPage = () => {
        if (page === 0) return
        fetchData(page - 1, pageSize)
        setPage(page - 1)
    }

    const submitModalForm = (e) => {
        e.preventDefault()
        const body = {
            "id": modalUserId,
            "jobTitle": modalJobTitle,
            "firstName": modalFirstName,
            "lastName": modalLastName,
            "email": modalEmail,
            "gender": modalGender,
            "company": modalCompany,
            "avatar": modalAvatarUrl
        }

        const url = `http://localhost:8080/api/user`;
        fetch(url, {
            method: "PUT",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        }).then(async (response) => {
            const data = await response.json()
            if (response.ok) {
                fetchData(page, pageSize)
                setShowModal(false)
            }
            console.log(data)
        }).catch((err) => {
            console.log(err)
            setShowModal(false)
        })
    }

    const fetchData = (currentPAge, currentPageSize) => {
        fetch(`http://localhost:8080/api/user?page=${currentPAge}&size=${currentPageSize}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }).then(async (response) => {
                const data = await response.json();
                setUsers(data)
            }).catch((err) => {
            console.log(err);
        })
    }

    useEffect(() => {
        fetchData(page, pageSize)
    }, [])

    return (
        <Wrapper>
            <Table>
                {showModal &&
                <Modal>
                    <ModalBody>
                        <SectionWrapper>
                            <InputWrapper>
                                <Label htmlFor='job'>Job Title</Label>
                                <Input id='job' onChange={(e) => setModalJobTitle(e.target.value)} value={modalJobTitle}/>
                            </InputWrapper>
                            <InputWrapper>
                                <Label htmlFor='firstName'>First Name</Label>
                                <Input id='firstName' onChange={(e) => setModalFirstName(e.target.value)} value={modalFirstName}/>
                            </InputWrapper>
                        </SectionWrapper>

                        <SectionWrapper>
                            <InputWrapper>
                                <Label htmlFor='lastName'>Last Name</Label>
                                <Input id='lastName' onChange={(e) => setModalLastName(e.target.value)} value={modalLastName}/>
                            </InputWrapper>
                            <InputWrapper>
                                <Label htmlFor='email'>Email</Label>
                                <Input id='email' onChange={(e) => setModalEmail(e.target.value)} value={modalEmail}/>
                            </InputWrapper>
                        </SectionWrapper>

                        <SectionWrapper>
                            <InputWrapper>
                                <Label htmlFor='gender'>Gender</Label>
                                <Input id='gender' onChange={(e) => setModalGender(e.target.value)} value={modalGender}/>
                            </InputWrapper>
                            <InputWrapper>
                                <Label htmlFor='company'>Company Name</Label>
                                <Input id='company' onChange={(e) => setModalCompany(e.target.value)} value={modalCompany}/>
                            </InputWrapper>
                        </SectionWrapper>

                        <SectionWrapper>
                            <InputWrapper>
                                <Label htmlFor='avatar'>Avatar url</Label>
                                <Input id='avatar' onChange={(e) => setModalAvatarUrl(e.target.value)} value={modalAvatarUrl}/>
                            </InputWrapper>
                        </SectionWrapper>

                        <SectionWrapper>
                            <InputWrapper center>
                                <ModalButton onClick={submitModalForm}>Edit User</ModalButton>
                            </InputWrapper>
                            <InputWrapper center>
                                <ModalButton close onClick={() => setShowModal(false)}>Close Modal</ModalButton>
                            </InputWrapper>
                        </SectionWrapper>
                    </ModalBody>
                </Modal>
                }
                <TableHeader>
                    <Column width='60px'>Avatar</Column>
                    <Column width='50px'>Id</Column>
                    <Column>First Name</Column>
                    <Column>Last Name</Column>
                    <Column width='150px'>Email</Column>
                    <Column>Gender</Column>
                    <Column>Company</Column>
                    <Column>Job Title</Column>
                    <Column>Role</Column>
                    <Column width='50px'>Edit</Column>
                    <Column width='50px'>Delete</Column>
                </TableHeader>
                {users && users.map((user, index) => (
                    <Row index={index} key={index}>
                        <Column width='60px'><Avatar src={user.avatar} alt="avatar"/></Column>
                        <Column width='50px'>{user.id}</Column>
                        <Column>{user.firstName}</Column>
                        <Column>{user.lastName}</Column>
                        <Column width='150px'>{user.email}</Column>
                        <Column>{user.gender}</Column>
                        <Column>{user.company}</Column>
                        <Column>{user.jobTitle}</Column>
                        <Column>{user.role.name}</Column>
                        <Column width='50px'>
                            <Button onClick={() => handleEdit(user)}><MdBorderColor/></Button>
                        </Column>
                        <Column width='50px'>
                            <Button delete onClick={() => handleDelete(user)}><MdDeleteForever/></Button>
                        </Column>
                    </Row>
                ))}
                <TableFooter>
                    <ButtonPagination onClick={prevPage}><MdArrowBack/></ButtonPagination>
                    <ButtonPagination onClick={nextPage}><MdArrowForward/></ButtonPagination>
                </TableFooter>
            </Table>
        </Wrapper>
    )
};

export default Crud