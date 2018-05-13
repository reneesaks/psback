package com.professionalstrangers.repository;

import com.professionalstrangers.ProfessionalStrangersApplication;
import com.professionalstrangers.domain.Advert;
import com.professionalstrangers.domain.enums.AdvertStatus;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProfessionalStrangersApplication.class)
@DataJpaTest
public class AdvertRepositoryTest {

    @Autowired
    private AdvertRepository advertRepository;

    @Before
    public void setAdverts() {

        Advert firstAdvert = new Advert();
        firstAdvert.setAdvertText("First Advert");
        firstAdvert.setAdvertStatus(AdvertStatus.NOT_ACCEPTED);

        Advert secondAdvert = new Advert();
        secondAdvert.setAdvertText("Second Advert");
        secondAdvert.setAdvertStatus(AdvertStatus.ACCEPTED);

        advertRepository.save(firstAdvert);
        advertRepository.save(secondAdvert);
    }

    @Test
    public void whenFindAllByAdvertStatusIsNot_thenReturnAdvertList() {

        // When
        List<Advert> firstAdvert = advertRepository.findAllByAdvertStatusIsNot(AdvertStatus.ACCEPTED);
        List<Advert> secondAdvert = advertRepository.findAllByAdvertStatusIsNot(AdvertStatus.NOT_ACCEPTED);

        //Then
        Assert.assertEquals(1, firstAdvert.size());
        Assert.assertEquals(1, secondAdvert.size());
        Assert.assertEquals(AdvertStatus.NOT_ACCEPTED, firstAdvert.get(0).getAdvertStatus());
        Assert.assertEquals(AdvertStatus.ACCEPTED, secondAdvert.get(0).getAdvertStatus());
    }

    @Test
    public void whenGetById_thenReturnAdvert() {

        // When
        Advert firstAdvert = advertRepository.getById(1L);
        Advert secondAdvert = advertRepository.getById(2L);

        //Then
        Assert.assertEquals("First Advert", firstAdvert.getAdvertText());
        Assert.assertEquals(AdvertStatus.NOT_ACCEPTED, firstAdvert.getAdvertStatus());
        Assert.assertEquals("Second Advert", secondAdvert.getAdvertText());
        Assert.assertEquals(AdvertStatus.ACCEPTED, secondAdvert.getAdvertStatus());
    }
}
