package com.anushka.androidtutz.contactmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.anushka.androidtutz.contactmanager.db.entity.Contact;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository contactRepository;

    public ContactViewModel(@NonNull Application application) {
        super(application);

        contactRepository = new ContactRepository(application);
    }

    public LiveData<List<Contact>> getAllContacts(){
        return contactRepository.getContactsLiveData();
    }

    public void create(String name, String email){
        contactRepository.createContact(name, email);
    }

    public void update(Contact contact){
        contactRepository.updateContact(contact);
    }

    public void delete(Contact contact){
        contactRepository.deleteContact(contact);
    }

    public void clear(){
        contactRepository.clear();
    }
}
