import {createContext, useContext, useEffect, useState} from "react";
import * as userService from "../services/user-service";

export const UserContext = createContext({
    user: {},
    dataChanged: false,
    getUser: () => {},
    saveUser: () => {},
    updateUser: () => {},
    deleteUser: () => {}
});

export default function UserProvider({children}) {
    const [user, setUser] = useState(null);

    const saveUserHandler = async () => {
        try {
            userService.saveUser(user)
                .then(r => setUser(r))
                .catch(e => console.error(e));
            return "User saved successfully";
        } catch (error) {
            console.error('Error while saving user', error);
            throw error;
        }
    }

    const updateUserHandler = async () => {
        try {
            userService.updateUser(user)
                .then(r => setUser(r))
                .catch(e => console.error(e));
            return "User updated successfully";
        } catch (error) {
            console.error('Error while updating user', error);
            throw error;
        }
    }

    const deleteUserHandler = async () => {
        try {
            await userService.deleteUser(user);
            return "User deleted successfully";
        } catch (error) {
            console.error('Error while deleting user', error);
            throw error;
        }
    }

    const getUserHandler = async (userId) => {
        try {
            userService.getUserById(userId)
                .then(r => {
                    setUser(r.data);
                })
                .catch(e => console.error(e));
            return "Users fetched successfully";
        } catch (error) {
            console.error('Error while fetching users', error);
            throw error;
        }
    }

    return (
        <UserContext.Provider value={{
            user: user,
            dataChanged: false,
            getUser: getUserHandler,
            saveUser: saveUserHandler,
            updateUser: updateUserHandler,
            deleteUser: deleteUserHandler,
        }}>
            {children}
        </UserContext.Provider>
    );
}