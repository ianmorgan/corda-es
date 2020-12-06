package net.corda.core.identity

import net.corda.core.internal.toAttributesMap
import net.corda.core.internal.toX500Name
import net.corda.core.internal.unspecifiedCountry
import org.bouncycastle.asn1.x500.style.BCStyle.*
import java.util.*
import javax.security.auth.x500.X500Principal


// Copied from Corda open source
data class CordaX500Name(val commonName: String?,
                         val organisationUnit: String?,
                         val organisation: String,
                         val locality: String,
                         val state: String?,
                         val country: String) {
    constructor(commonName: String, organisation: String, locality: String, country: String) :
            this(commonName = commonName, organisationUnit = null, organisation = organisation, locality = locality, state = null, country = country)

    /**
     * @param organisation name of the organisation.
     * @param locality locality of the organisation, typically nearest major city.
     * @param country country the organisation is in, as an ISO 3166-1 2-letter country code.
     */
    constructor(organisation: String, locality: String, country: String) : this(null, null, organisation, locality, null, country)

    init {
        // Legal name checks.
        //LegalNameValidator.validateOrganization(organisation, LegalNameValidator.Validation.MINIMAL)

        require(country in countryCodes) { "Invalid country code $country" }

        require(organisation.length < MAX_LENGTH_ORGANISATION) {
            "Organisation attribute (O) must contain less then $MAX_LENGTH_ORGANISATION characters."
        }
        require(locality.length < MAX_LENGTH_LOCALITY) { "Locality attribute (L) must contain less then $MAX_LENGTH_LOCALITY characters." }

        state?.let { require(it.length < MAX_LENGTH_STATE) { "State attribute (ST) must contain less then $MAX_LENGTH_STATE characters." } }
        organisationUnit?.let {
            require(it.length < MAX_LENGTH_ORGANISATION_UNIT) {
                "Organisation Unit attribute (OU) must contain less then $MAX_LENGTH_ORGANISATION_UNIT characters."
            }
        }
        commonName?.let {
            require(it.length < MAX_LENGTH_COMMON_NAME) {
                "Common Name attribute (CN) must contain less then $MAX_LENGTH_COMMON_NAME characters."
            }
        }
    }

    companion object {

        @Deprecated("Not Used")
        const val LENGTH_COUNTRY = 2
        const val MAX_LENGTH_ORGANISATION = 128
        const val MAX_LENGTH_LOCALITY = 64
        const val MAX_LENGTH_STATE = 64
        const val MAX_LENGTH_ORGANISATION_UNIT = 64
        const val MAX_LENGTH_COMMON_NAME = 64

        private val supportedAttributes = setOf(O, C, L, CN, ST, OU)
        private val countryCodes: Set<String> = setOf(*Locale.getISOCountries(), unspecifiedCountry)

        @JvmStatic
        fun build(principal: X500Principal): CordaX500Name {
            val attrsMap = principal.toAttributesMap(supportedAttributes)
            val CN = attrsMap[CN]
            val OU = attrsMap[OU]
            val O = requireNotNull(attrsMap[O]) { "Corda X.500 names must include an O attribute" }
            val L = requireNotNull(attrsMap[L]) { "Corda X.500 names must include an L attribute" }
            val ST = attrsMap[ST]
            val C = requireNotNull(attrsMap[C]) { "Corda X.500 names must include an C attribute" }
            return CordaX500Name(CN, OU, O, L, ST, C)
        }

        @JvmStatic
        fun parse(name: String): CordaX500Name = build(X500Principal(name))
    }

    @Transient
    private var _x500Principal: X500Principal? = null

    /** Return the [X500Principal] equivalent of this name. */
    val x500Principal: X500Principal
        get() {
            return _x500Principal ?: X500Principal(this.toX500Name().encoded).also { _x500Principal = it }
        }

    override fun toString(): String = x500Principal.toString()
}