package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class ContactList {
    private ArrayList<Contact> contacts;
    private String FILENAME= "contacts.sav";

    public ContactList(){
        contacts= new ArrayList<Contact>();
    }
    public void setContacts(ArrayList<Contact>contact_list){
        this.contacts=contact_list;
    }
    public ArrayList<Contact> getContacts(){
        return this.contacts;
    }
    public ArrayList<String> getAllUsernames(){
        ArrayList<String>ans=new ArrayList<String>();
        for(Contact c:contacts){
            ans.add(c.getUsername());
        }
        return ans;
    }
    public void addContact(Contact c){
        contacts.add(c);
    }
    public void deleteContact(Contact c){
        contacts.remove(c);
    }
    public Contact getContact(int index){
        return contacts.get(index);
    }
    public int getSize(){
        return contacts.size();
    }
    public int getIndex(Contact c){
        int pos=0;
        for(Contact x:contacts){
            if(x.getId().equals(c.getId())){
                return pos;
            }
            pos++;
        }
        return -1;
    }
    public boolean hasContact(Contact c){
        return contacts.contains(c);
    }
    public Contact getContactByUsername(String username){
        for(Contact c:contacts){
            if(c.getUsername().equals(username)){
                return c;
            }
        }
        return null;
    }

    public void loadContacts(Context context) {

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            contacts = new ArrayList<Contact>();
        }
    }

    public void saveContacts(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable(String username){
        for(Contact c:contacts){
            if(c.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }



}
