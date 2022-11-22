package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.ImportOfferDTO;
import softuni.exam.models.dtos.ImportOffersRootDTO;
import softuni.exam.models.entities.Agent;
import softuni.exam.models.entities.Apartment;
import softuni.exam.models.entities.ApartmentType;
import softuni.exam.models.entities.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.util.ErrorMessages.INVALID_OFFER;
import static softuni.exam.util.FilesPaths.OFFERS_PATH;

@Service
public class OfferServiceImpl implements OfferService {
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final OfferRepository offerRepository;
    private final AgentService agentService;
    private final ApartmentService apartmentService;
    private final XmlParser xmlParser;
    public OfferServiceImpl(ModelMapper mapper, ValidationUtil validationUtil, OfferRepository offerRepository, AgentService agentRepository, ApartmentService apartmentService, XmlParser xmlParser) {
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.offerRepository = offerRepository;
        this.agentService = agentRepository;
        this.apartmentService = apartmentService;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(OFFERS_PATH);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        ImportOffersRootDTO importOffersRootDTO = xmlParser.fromFile(OFFERS_PATH.toAbsolutePath().toString(), ImportOffersRootDTO.class);
        return importOffersRootDTO.getOffers()
                .stream()
                .map(this::importOffer)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String exportOffers() {
        List<Offer> offers = this.offerRepository.findAllByApartmentApartmentTypeOrderByApartmentAreaDescPriceAsc(ApartmentType.three_rooms);

        return offers
                .stream()
                .map(this::exportOffer)
                .collect(Collectors.joining("\n"));
    }

    private String exportOffer(Offer offer) {
        return String.format("Agent %s %s with offer №%d:\n" +
                        "\t-Apartment area: %.2f\n" +
                        "\t--Town: %s\n" +
                        "\t---Price: %.2f",
                offer.getAgent().getFirstName(),
                offer.getAgent().getLastName(),
                offer.getId(),
                offer.getApartment().getArea(),
                offer.getApartment().getTown().getTownName(),
                offer.getPrice());
    }

    private String importOffer(ImportOfferDTO importOfferDTO) {
        if (!validationUtil.isValid(importOfferDTO)) {
            return INVALID_OFFER;
        }

        Optional<Agent> agentByFirstName = agentService.findAgentByFirstName(importOfferDTO.getAgent().getName());
        if (agentByFirstName.isEmpty()) {
            return INVALID_OFFER;
        }

        Apartment apartment = apartmentService.findById(importOfferDTO.getApartment().getId());
        Agent agent = agentByFirstName.get();

        Offer offer = mapper.map(importOfferDTO, Offer.class);
        offer.setAgent(agent);
        offer.setApartment(apartment);
        this.offerRepository.save(offer);
        return String.format("Successfully imported offer %.2f", offer.getPrice());
    }
}
