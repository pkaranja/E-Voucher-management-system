package co.tz.qroo.zawadi.theme.service;

import co.tz.qroo.zawadi.giftcard.domain.Giftcard;
import co.tz.qroo.zawadi.giftcard.repos.GiftcardRepository;
import co.tz.qroo.zawadi.theme.domain.Theme;
import co.tz.qroo.zawadi.theme.model.ThemeDTO;
import co.tz.qroo.zawadi.theme.repos.ThemeRepository;
import co.tz.qroo.zawadi.util.NotFoundException;
import co.tz.qroo.zawadi.util.ReferencedWarning;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final GiftcardRepository giftcardRepository;

    public ThemeService(final ThemeRepository themeRepository,
            final GiftcardRepository giftcardRepository) {
        this.themeRepository = themeRepository;
        this.giftcardRepository = giftcardRepository;
    }

    public List<ThemeDTO> findAll() {
        final List<Theme> themes = themeRepository.findAll(Sort.by("id"));
        return themes.stream()
                .map(theme -> mapToDTO(theme, new ThemeDTO()))
                .toList();
    }

    public ThemeDTO get(final UUID id) {
        return themeRepository.findById(id)
                .map(theme -> mapToDTO(theme, new ThemeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final ThemeDTO themeDTO) {
        final Theme theme = new Theme();
        mapToEntity(themeDTO, theme);
        return themeRepository.save(theme).getId();
    }

    public void update(final UUID id, final ThemeDTO themeDTO) {
        final Theme theme = themeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(themeDTO, theme);
        themeRepository.save(theme);
    }

    public void delete(final UUID id) {
        themeRepository.deleteById(id);
    }

    private ThemeDTO mapToDTO(final Theme theme, final ThemeDTO themeDTO) {
        themeDTO.setId(theme.getId());
        themeDTO.setBackgroundImage(theme.getBackgroundImage());
        themeDTO.setFontColor(theme.getFontColor());
        themeDTO.setPrimaryColor(theme.getPrimaryColor());
        themeDTO.setSecondaryColor(theme.getSecondaryColor());
        themeDTO.setGoogleFont(theme.getGoogleFont());
        return themeDTO;
    }

    private Theme mapToEntity(final ThemeDTO themeDTO, final Theme theme) {
        theme.setBackgroundImage(themeDTO.getBackgroundImage());
        theme.setFontColor(themeDTO.getFontColor());
        theme.setPrimaryColor(themeDTO.getPrimaryColor());
        theme.setSecondaryColor(themeDTO.getSecondaryColor());
        theme.setGoogleFont(themeDTO.getGoogleFont());
        return theme;
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Theme theme = themeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Giftcard themeGiftcard = giftcardRepository.findFirstByTheme(theme);
        if (themeGiftcard != null) {
            referencedWarning.setKey("theme.giftcard.theme.referenced");
            referencedWarning.addParam(themeGiftcard.getId());
            return referencedWarning;
        }
        return null;
    }

}
